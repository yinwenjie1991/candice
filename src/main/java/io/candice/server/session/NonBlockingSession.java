package io.candice.server.session;

import io.candice.config.ErrorCode;
import io.candice.net.connection.FrontendConnection;
import io.candice.net.connection.MySQLConnection;
import io.candice.net.connection.ServerConnection;
import io.candice.net.mysql.OkPacket;
import io.candice.route.RouteResultset;
import io.candice.route.RouteResultsetNode;
import io.candice.server.mysql.handler.*;
import io.candice.server.parser.ServerParse;
import org.apache.log4j.Logger;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-03
 */
public class NonBlockingSession implements Session {

    private static final Logger LOGGER = Logger.getLogger(NonBlockingSession.class);

    private final ServerConnection source;
    private final ConcurrentHashMap<RouteResultsetNode, MySQLConnection> target;
    private final AtomicBoolean terminating;

    // life-cycle: each sql execution
    private volatile SingleNodeHandler singleNodeHandler;
    private volatile MultiNodeQueryHandler multiNodeHandler;
    private volatile CommitNodeHandler commitHandler;
    private volatile RollbackNodeHandler rollbackHandler;

    public NonBlockingSession(ServerConnection source) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("-->new nonblocking session:" + source.getHost() + "," + source.getPort());
        }
        this.source = source;
        this.target = new ConcurrentHashMap<RouteResultsetNode, MySQLConnection>();
        this.terminating = new AtomicBoolean(false);
    }

    public ServerConnection getSource() {
        return source;
    }

    public int getTargetCount() {
        return target.size();
    }

    public Set<RouteResultsetNode> getTargetKeys() {
        return target.keySet();
    }

    public MySQLConnection getTarget(RouteResultsetNode key) {
        return target.get(key);
    }

    public MySQLConnection removeTarget(RouteResultsetNode key) {
        return target.remove(key);
    }

    public void execute(RouteResultset rrs, String sql, int type) {
        if (LOGGER.isDebugEnabled()) {
            StringBuilder s = new StringBuilder();
            LOGGER.debug(s.append(source).append(rrs).append("->executing").toString());
        }

        RouteResultsetNode[] nodes = rrs.getNodes();
        if (nodes == null || nodes.length == 0) {
            source.writeErrMessage(ErrorCode.ER_NO_DB_ERROR, "No dataNode selected");
            return;
        }
        try {
            if (nodes.length == 1 && nodes[0].getSqlCount() == 1) {
                //单点路由
                singleNodeHandler = new SingleNodeHandler(nodes[0], this);
                singleNodeHandler.execute();
            } else {
                boolean autocommit = source.isAutocommit();
                if (autocommit && isModifySQL(type)) {
                    autocommit = false;
                }
                multiNodeHandler = new MultiNodeQueryHandler(nodes, autocommit, this);
                multiNodeHandler.execute();
            }
        } catch (Exception e) {
            LOGGER.error("sql[" + sql + "]异常", e);
            e.printStackTrace();
            source.writeErrMessage(ErrorCode.ER_EXEC_ERROR, "NoBlockingSession execute error!");
        }
    }

    public void commit() {
        final int initCount = target.size();
        if (initCount <= 0) {
            source.write(OkPacket.OK);
            return;
        }
        commitHandler = new CommitNodeHandler(this);
        commitHandler.commit();
    }

    public void rollback() {
        final int initCount = target.size();
        if (initCount <= 0) {
            source.write(OkPacket.OK);
            return;
        }
        rollbackHandler = new RollbackNodeHandler(this);
        rollbackHandler.rollback();
    }

    public void cancel(FrontendConnection sponsor) {

    }

    public void terminate() {

    }

    public boolean closeConnection(RouteResultsetNode key) {
        MySQLConnection conn = target.remove(key);
        if (conn != null) {
            conn.close();
            return true;
        }
        return false;
    }

    public void setConnectionRunning(RouteResultsetNode[] route) {
        for (RouteResultsetNode rrn : route) {
            MySQLConnection c = target.get(rrn);
            if (c != null) {
                c.setRunning(true);
            }
        }
    }

    public MySQLConnection bindConnection(RouteResultsetNode key, MySQLConnection conn) {
        return target.put(key, conn);
    }

    public void clearConnections() {
        clearConnections(true);
    }

    private void clearConnections(boolean pessimisticRelease) {
        for (RouteResultsetNode node : target.keySet()) {
            MySQLConnection c = target.remove(node);

            if (c == null || c.isClosedOrQuit()) {
                continue;
            }

            // 如果通道正在运行中，则关闭当前通道。
            // 清空连接目前只需要释放并不关闭
//            if (c.isRunning() || (pessimisticRelease && closed())) {
//                c.close();
//                continue;
//            }
            // 非事务中的通道，直接释放资源。
            if (c.isAutocommit()) {
                c.release();
                continue;
            }
            //事务则回滚
            c.setHandler(new RollbackReleaseHandler());
            c.rollback();
        }
    }

    public void releaseConnections() {
        for (RouteResultsetNode rrn : target.keySet()) {
            MySQLConnection c = target.remove(rrn);
            if (c != null) {
                if (c.isRunning()) {
                    c.close();
                    try {
                        throw new IllegalStateException("running connection is found: " + c);
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                } else if (!c.isClosedOrQuit()) {
                    if (source.isClosed()) {
                        c.quit();
                    } else {
                        c.release();
                    }
                }
            }
        }
    }


    public boolean closed() {
        if ( source == null || source.getChannelHandlerContext() == null) {
            return true;
        }
        return !source.getChannelHandlerContext().channel().isOpen();
    }

    private static boolean isModifySQL(int type) {
        switch (type) {
            case ServerParse.INSERT:
            case ServerParse.DELETE:
            case ServerParse.UPDATE:
            case ServerParse.REPLACE:
                return true;
            default:
                return false;
        }
    }
}
