package io.candice.net.connection;

import io.candice.CandiceServer;
import io.candice.config.ErrorCode;
import io.candice.config.model.SchemaConfig;
import io.candice.route.CandiceServerRouter;
import io.candice.route.RouteResultset;
import io.candice.server.mysql.handler.RollbackNodeHandler;
import io.candice.server.session.NonBlockingSession;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;
import java.sql.SQLNonTransientException;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-17
 */
public class ServerConnection extends FrontendConnection {
    private static final Logger LOGGER = Logger.getLogger(ServerConnection.class);
    private static final Logger routeLogger = Logger.getLogger("route-digest");

    private volatile int txIsolation;
    private volatile boolean autocommit;
    private volatile boolean txInterrupted;
    private long lastInsertId;
    private NonBlockingSession session;

    public ServerConnection() {
        this.txInterrupted = false;
        this.autocommit = true;
    }

    public void execute(String sql, int type) {
        if (txInterrupted) {
            writeErrMessage(ErrorCode.ER_YES, "Transaction error, need to rollback.");
            return;
        }
        String db = this.schema;
        if (db == null) {
            writeErrMessage(ErrorCode.ER_BAD_DB_ERROR, "Unknown database '" + db + "'");
            return;
        }
        SchemaConfig schema = CandiceServer.getInstance().getConfig().getSchemas().get(db);
        if (schema == null) {
            writeErrMessage(ErrorCode.ER_BAD_DB_ERROR, "Unknown database '" + db + "'");
            return;
        }

        //路由计算
        RouteResultset rrs = null;
        long st = System.currentTimeMillis();
        long et = -1L;
        try {
            rrs = CandiceServerRouter.route(schema, sql, this.charset, this);
        } catch (SQLNonTransientException e) {
            StringBuilder s = new StringBuilder();
            LOGGER.warn(s.append(this).append(sql).toString(), e);
            String msg = e.getMessage();
            writeErrMessage(ErrorCode.ER_PARSE_ERROR, msg == null ? e.getClass().getSimpleName() : msg);
        } finally {
            et = System.currentTimeMillis();
            routeLogger.info((et - st) + "ms,[" + sql + "]");
        }
        getSession().execute(rrs, sql, type);
    }

    /**
     * 提交事务
     */
    public void commit() {
        if (txInterrupted) {
            writeErrMessage(ErrorCode.ER_YES, "Transaction error, need to rollback.");
        } else {
            getSession().commit();
        }
    }

    /**
     * 回滚事务
     */
    public void rollback() {
        // 状态检查
        if (txInterrupted) {
            txInterrupted = false;
        }

        // 执行回滚
        getSession().rollback();
    }

    public int getTxIsolation() {
        return txIsolation;
    }

    public boolean isAutocommit() {
        return autocommit;
    }

    public boolean isTxInterrupted() {
        return txInterrupted;
    }

    public void setTxIsolation(int txIsolation) {
        this.txIsolation = txIsolation;
    }

    public void setAutocommit(boolean autocommit) {
        this.autocommit = autocommit;
    }

    public void setTxInterrupted(boolean txInterrupted) {
        this.txInterrupted = txInterrupted;
    }

    public long getLastInsertId() {
        return lastInsertId;
    }

    public void setLastInsertId(long lastInsertId) {
        this.lastInsertId = lastInsertId;
    }

    public NonBlockingSession getSession() {
        return session;
    }

    public void setSession(NonBlockingSession session) {
        this.session = session;
    }

}
