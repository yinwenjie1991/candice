package io.candice.server.handler;

import io.candice.config.ErrorCode;
import io.candice.net.connection.ServerConnection;
import io.candice.server.parser.ServerParse;
import org.apache.log4j.Logger;

/**
 * 文件描述: 查询处理
 * 作者: yinwenjie
 * 日期: 2017-10-17
 */
public class ServerQueryHandler implements FrontendQueryHandler {
    private static final Logger LOGGER = Logger.getLogger(ServerQueryHandler.class);

    private final ServerConnection connection;

    public ServerQueryHandler(ServerConnection connection) {
        this.connection = connection;
    }

    public void query(String sql) {
        ServerConnection c = this.connection;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(new StringBuilder().append(c).append(sql).toString());
        }
        int rs = ServerParse.parse(sql);
        switch (rs & 0xff) {
            case ServerParse.EXPLAIN:
                ExplainHandler.handle(sql, c, rs >>> 8);
                break;
            case ServerParse.SET:
                SetHandler.handle(sql, c, rs >>> 8);
                break;
            case ServerParse.SHOW:
                ShowHandler.handle(sql, c, rs >>> 8);
                break;
            case ServerParse.SELECT:
                SelectHandler.handle(sql, c, rs >>> 8);
                break;
            case ServerParse.START:
                StartHandler.handle(sql, c, rs >>> 8);
                break;
            case ServerParse.BEGIN:
                BeginHandler.handle(sql, c);
                break;
            case ServerParse.SAVEPOINT:
                SavepointHandler.handle(sql, c);
                break;
            case ServerParse.KILL:
                KillHandler.handle(sql, rs >>> 8, c);
                break;
            case ServerParse.KILL_QUERY:
                c.writeErrMessage(ErrorCode.ER_UNKNOWN_COM_ERROR, "Unsupported command");
                break;
            case ServerParse.USE:
                UseHandler.handle(sql, c, rs >>> 8);
                break;
            case ServerParse.COMMIT:
                c.commit();
                break;
            case ServerParse.ROLLBACK:
                c.rollback();
                break;
            default:
                c.execute(sql, rs);
        }

    }
}
