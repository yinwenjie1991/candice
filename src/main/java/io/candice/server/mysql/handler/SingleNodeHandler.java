package io.candice.server.mysql.handler;

import io.candice.CandiceServer;
import io.candice.config.CandiceConfig;
import io.candice.config.ErrorCode;
import io.candice.net.connection.BackendConnection;
import io.candice.net.connection.MySQLConnection;
import io.candice.net.connection.ServerConnection;
import io.candice.net.mysql.ErrorPacket;
import io.candice.net.mysql.OkPacket;
import io.candice.route.RouteResultsetNode;
import io.candice.server.mysql.MySQLDataNode;
import io.candice.server.session.NonBlockingSession;
import io.candice.util.StringUtil;
import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-03
 */
public class SingleNodeHandler implements ResponseHandler, Terminatable{

    private static final Logger LOGGER             = Logger.getLogger(SingleNodeHandler.class);

    private final RouteResultsetNode route;
    private final NonBlockingSession session;
    private byte packetId;
    private ReentrantLock lock = new ReentrantLock();
    private boolean isRunning;
    private Runnable terminateCallBack;

    public SingleNodeHandler(RouteResultsetNode route, NonBlockingSession session) {
        if (route == null) {
            throw new IllegalArgumentException("routeNode is null!");
        }
        if (session == null) {
            throw new IllegalArgumentException("session is null!");
        }
        this.route = route;
        this.session = session;
    }

    public void terminate(Runnable callback) {
        boolean zeroReached = false;
        lock.lock();
        try {
            if (isRunning) {
                terminateCallBack = callback;
            } else {
                zeroReached = true;
            }
        } finally {
            lock.unlock();
        }
        if (zeroReached) {
            callback.run();
        }
    }

    private void endRunning() {
        Runnable callback = null;
        lock.lock();
        try {
            if (isRunning) {
                isRunning = false;
                callback = terminateCallBack;
                terminateCallBack = null;
            }
        } finally {
            lock.unlock();
        }
        if (callback != null) {
            callback.run();
        }
    }

    public void execute() throws Exception {
        lock.lock();
        try {
            this.isRunning = true;
            this.packetId = 0;
//            this.buffer = session.getSource().allocate();
        } finally {
            lock.unlock();
        }
        final MySQLConnection conn = session.getTarget(route);
        if (conn == null) {
            CandiceConfig conf = CandiceServer.getInstance().getConfig();
            MySQLDataNode dn = conf.getDataNodes().get(route.getName());
            dn.getConnection(this, null);
        } else {
            conn.setRunning(true);
            _execute(conn);
        }
    }

    public void connectionAcquired(MySQLConnection conn) {
        conn.setRunning(true);
        session.bindConnection(route, conn);
        _execute(conn);
    }

    private void _execute(MySQLConnection conn) {
        if (session.closed()) {
            conn.setRunning(false);
            endRunning();
            session.clearConnections();
            return;
        }
        conn.setHandler(this);
        try {
            conn.execute(route, session.getSource(), session.getSource().isAutocommit());
        } catch (UnsupportedEncodingException e1) {
            LOGGER.error("unsupport encoding["+route.getStatement()[0]+"]", e1);
            executeException(conn);
            return;
        }
    }

    private void executeException(MySQLConnection c) {
        c.setRunning(false);
        endRunning();
        session.clearConnections();
        ErrorPacket err = new ErrorPacket();
        err.packetId = ++packetId;
        err.errno = ErrorCode.ER_YES;
        err.message = StringUtil.encode("unknown backend charset: " + c.getCharset(), session.getSource().getCharset());
        ServerConnection source = session.getSource();
        err.write(source.getChannelHandlerContext());
    }

    public void connectionError(Throwable e, MySQLConnection conn) {
        if (!session.closeConnection(route)) {
            conn.close();
        }
        endRunning();
        ErrorPacket err = new ErrorPacket();
        err.packetId = ++packetId;
        err.errno = ErrorCode.ER_YES;
        err.message = StringUtil.encode(e.getMessage(), session.getSource().getCharset());
        ServerConnection source = session.getSource();
        err.write(source.getChannelHandlerContext());
    }

    public void errorResponse(byte[] err, MySQLConnection conn) {
        conn.setRunning(false);
        if (conn.isAutocommit()) {
            session.clearConnections();
        }
        endRunning();
        session.getSource().write(err);
    }

    public void okResponse(byte[] ok, MySQLConnection conn) {
//        boolean executeResponse = false;
//        try {
//            executeResponse = conn.syncAndExcute();
//        } catch (UnsupportedEncodingException e) {
//            executeException(conn);
//        }
//        if (executeResponse) {
//            conn.setRunning(false);
//            ServerConnection source = session.getSource();
//            if (source.isAutocommit()) {
//                session.clearConnections();
//            }
//            endRunning();
//            OkPacket ok = new OkPacket();
//            ok.read(data);
//            source.setLastInsertId(ok.insertId);
//            buffer = source.writeToBuffer(data, buffer);
//            source.write(buffer);
//        }
        conn.setRunning(false);
        ServerConnection source = session.getSource();
        if (source.isAutocommit()) {
            session.clearConnections();
        }
        endRunning();
        OkPacket okPacket = new OkPacket();
        okPacket.read(ok);
        source.setLastInsertId(okPacket.insertId);
        okPacket.write(source.getChannelHandlerContext());
    }

    public void fieldEofResponse(byte[] header, List<byte[]> fields, byte[] eof, MySQLConnection conn) {
        ServerConnection source = session.getSource();
        ++packetId;
        ByteBuf buf = source.getChannelHandlerContext().alloc().buffer();
        buf.writeBytes(header);
        for (int i = 0, len = fields.size(); i < len; ++i) {
            ++packetId;
//            buffer = source.writeToBuffer(fields.get(i), buffer);
            buf.writeBytes(fields.get(i));
        }
        ++packetId;
//        buffer = source.writeToBuffer(eof, buffer);
        buf.writeBytes(eof);
        source.writeByteBuf(buf);
    }

    public void rowResponse(byte[] row, MySQLConnection conn) {
        ++packetId;
//        buffer = session.getSource().writeToBuffer(row, buffer);
        session.getSource().write(row);
    }

    public void rowEofResponse(byte[] eof, MySQLConnection conn) {
        ServerConnection source = session.getSource();
        conn.setRunning(false);
//        conn.recordSql(source.getHost(), source.getSchema(), route.getStatement());
        if (source.isAutocommit()) {
            session.clearConnections();
        }
        endRunning();
        source.write(eof);
    }

}
