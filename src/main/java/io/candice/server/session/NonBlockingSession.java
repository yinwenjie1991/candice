package io.candice.server.session;

import io.candice.config.ErrorCode;
import io.candice.net.connection.FrontendConnection;
import io.candice.net.connection.MySQLConnection;
import io.candice.net.connection.ServerConnection;
import io.candice.route.RouteResultset;
import io.candice.route.RouteResultsetNode;
import io.candice.server.mysql.handler.CommitNodeHandler;
import io.candice.server.mysql.handler.MultiNodeQueryHandler;
import io.candice.server.mysql.handler.RollbackNodeHandler;
import io.candice.server.mysql.handler.SingleNodeHandler;
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

            }
        } catch (Exception e) {
            LOGGER.error("sql[" + sql + "]异常", e);
            source.writeErrMessage(ErrorCode.ER_EXEC_ERROR, "NoBlockingSession execute error!");
        }
    }

    public void commit() {

    }

    public void rollback() {

    }

    public void cancel(FrontendConnection sponsor) {

    }

    public void terminate() {

    }
}
