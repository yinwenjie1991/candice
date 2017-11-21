package io.candice.server.handler;

import io.candice.net.connection.ServerConnection;
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


    }
}
