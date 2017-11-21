package io.candice.server.mysql.handler;

import io.candice.CandiceServer;
import io.candice.config.CandiceConfig;
import io.candice.net.connection.MySQLConnection;
import io.candice.net.connection.ServerConnection;
import io.candice.net.mysql.ErrorPacket;
import io.candice.net.mysql.OkPacket;
import io.candice.route.RouteResultsetNode;
import io.candice.server.mysql.MySQLDataNode;
import io.candice.server.session.NonBlockingSession;
import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-03
 */
public class MultiNodeQueryHandler extends MultiNodeHandler{

    private static final Logger LOGGER = Logger.getLogger(MultiNodeQueryHandler.class);

    private final RouteResultsetNode[] route;
    private final NonBlockingSession session;
    private final boolean autocommit;
    private final CommitNodeHandler icHandler;

    public MultiNodeQueryHandler(RouteResultsetNode[] route, boolean autocommit, NonBlockingSession session) {
        super(session);
        if (route == null) {
            throw new IllegalArgumentException("routeNode is null!");
        }
        this.session = session;
        this.route = route;
        this.autocommit = autocommit;
        this.lock = new ReentrantLock();
        this.icHandler = new CommitNodeHandler(session);
    }

    private final ReentrantLock lock;
    private long affectedRows;
    private long insertId;
//    private ByteBuffer buffer;
    private boolean fieldsReturned;

    public void execute() throws Exception {
        final ReentrantLock lock = this.lock;
        lock.lock();
        int initialCount=0;
        try {
            for (RouteResultsetNode rrn : route) {
                initialCount+=rrn.getSqlCount();
            }
            this.reset(initialCount);
            this.fieldsReturned = false;
            this.affectedRows = 0L;
            this.insertId = 0L;
//            this.buffer = session.getSource().allocate();
        } finally {
            lock.unlock();
        }

        if (session.closed()) {
            decrementCountToZero();
            recycleResources();
            return;
        }

        session.setConnectionRunning(route);

//        ThreadPoolExecutor executor = session.getSource().getProcessor().getExecutor();
        for (final RouteResultsetNode node : route) {
            final MySQLConnection conn = session.getTarget(node);
            if (conn != null) {
                conn.setAttachment(node);
                _execute(conn, node);
            } else {
                CandiceConfig conf = CandiceServer.getInstance().getConfig();
                MySQLDataNode dn = conf.getDataNodes().get(node.getName());
                dn.getConnection(this, node);
            }
        }
    }

    private void _execute(MySQLConnection conn, RouteResultsetNode node) {
        conn.setHandler(this);

        if (session.closed()) {
            backendConnError(conn, "failed or cancelled by other thread");
            return;
        }

        try {
            conn.execute(node, session.getSource(), autocommit);
        } catch (IOException e) {
            connectionError(e, conn);
        }
    }

    @Override
    protected void recycleResources() {
//        ByteBuffer buf;
//        lock.lock();
//        try {
//            buf = buffer;
//            if (buf != null) {
//                buffer = null;
//            }
//        } finally {
//            lock.unlock();
//        }
//        if (buf != null) {
//            session.getSource().recycle(buf);
//        }
    }

    @Override
    public void connectionAcquired(final MySQLConnection conn) {
        Object attachment = conn.getAttachment();
        if (!(attachment instanceof RouteResultsetNode)) {
            backendConnError(conn, new StringBuilder().append("wrong attachement from connection build: ").append(conn)
                    .append(" bound by ").append(session.getSource()).toString());
            conn.release();
            return;
        }
        final RouteResultsetNode node = (RouteResultsetNode) attachment;
        conn.setRunning(true);
        session.bindConnection(node, conn);
        _execute(conn, node);
    }

    @Override
    public void connectionError(Throwable e, MySQLConnection conn) {
        backendConnError(conn, "connection err!");
    }

    @Override
    public void errorResponse(byte[] data, MySQLConnection conn) {
        ErrorPacket err = new ErrorPacket();
        err.read(data);
        backendConnError(conn, err);
    }

    @Override
    public void okResponse(byte[] data, MySQLConnection conn) {
        ServerConnection source = session.getSource();
        conn.setRunning(false);
        Object attachment = conn.getAttachment();
        if (attachment instanceof RouteResultsetNode) {
            RouteResultsetNode node = (RouteResultsetNode) attachment;
//            conn.recordSql(source.getHost(), source.getSchema(), node.getStatement());
        } else {
            LOGGER.warn(new StringBuilder().append("back-end conn: ").append(conn)
                    .append(" has wrong attachment: ").append(attachment).append(", for front-end conn: ")
                    .append(source));
        }
        OkPacket ok = new OkPacket();
        ok.read(data);
        lock.lock();
        try {
            affectedRows += ok.affectedRows;
            if (ok.insertId > 0) {
                insertId = (insertId == 0) ? ok.insertId : Math.min(insertId, ok.insertId);
            }
        } finally {
            lock.unlock();
        }
        if (decrementCountBy(1)) {
            if (isFail.get()) {
                notifyError();
                return;
            }
            try {
                recycleResources();
                ok.packetId = ++packetId;// OK_PACKET
                ok.affectedRows = affectedRows;
                if (insertId > 0) {
                    ok.insertId = insertId;
                    source.setLastInsertId(insertId);
                }

                if (source.isAutocommit()) {
                    if (!autocommit) { // 前端非事务模式，后端事务模式，则需要自动递交后端事务。
                        icHandler.commit();
                    } else {
                        session.releaseConnections();
                        ok.write(source.getChannelHandlerContext());
                    }
                } else {
                    ok.write(source.getChannelHandlerContext());
                }
            } catch (Exception e) {
                LOGGER.warn("exception happens in success notification: " + session.getSource(), e);
            }
        }

    }

    @Override
    public void rowEofResponse(byte[] eof, MySQLConnection conn) {
        conn.setRunning(false);
        ServerConnection source = session.getSource();
        RouteResultsetNode node = null;
        Object attachment = conn.getAttachment();
        if (attachment instanceof RouteResultsetNode) {
            node = (RouteResultsetNode) attachment;
//            conn.recordSql(source.getHost(), source.getSchema(), node.getStatement());
        } else {
            LOGGER.warn(new StringBuilder().append("back-end conn: ").append(conn).append(" has wrong attachment: ")
                    .append(attachment).append(", for front-end conn: ").append(source));
        }
        if (source.isAutocommit()) {
            if (node != null) {
                conn = session.removeTarget(node);
                if (conn != null) {
                    if (isFail.get() || session.closed()) {
                        conn.quit();
                    } else {
                        conn.release();
                    }
                }
            }
        }
        if (decrementCountBy(1)) {
            if (isFail.get()) {
                notifyError();
                recycleResources();
                return;
            }
            try {
                if (source.isAutocommit()) {
                    session.releaseConnections();
                }
                eof[3] = ++packetId;

                source.write(eof);
            } catch (Exception e) {
                LOGGER.warn("exception happens in success notification: " + session.getSource(), e);
            }
        }
    }

    @Override
    public void fieldEofResponse(byte[] header, List<byte[]> fields, byte[] eof, MySQLConnection conn) {
        lock.lock();
        try {
            if (fieldsReturned) {
                return;
            }
            fieldsReturned = true;
            header[3] = ++packetId;
            ServerConnection source = session.getSource();
//            buffer = source.writeToBuffer(header, buffer);
            ByteBuf buf = source.getChannelHandlerContext().alloc().buffer();
            for (int i = 0, len = fields.size(); i < len; ++i) {
                byte[] field = fields.get(i);
                field[3] = ++packetId;
//                buffer = source.writeToBuffer(field, buffer);
                buf.writeBytes(field);
            }
            eof[3] = ++packetId;
//            buffer = source.writeToBuffer(eof, buffer);
            buf.writeBytes(eof);
            source.writeByteBuf(buf);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void rowResponse(byte[] row, MySQLConnection conn) {
        lock.lock();
        try {
            row[3] = ++packetId;
//            buffer = session.getSource().writeToBuffer(row, buffer);
            conn.write(row);
        } finally {
            lock.unlock();
        }
    }
}
