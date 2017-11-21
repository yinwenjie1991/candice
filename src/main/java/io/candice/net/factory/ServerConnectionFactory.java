package io.candice.net.factory;

import io.candice.CandiceServer;
import io.candice.config.model.SystemConfig;
import io.candice.net.connection.FrontendConnection;
import io.candice.net.connection.ServerConnection;
import io.candice.server.handler.ServerQueryHandler;
import io.candice.server.session.NonBlockingSession;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-17
 */
public class ServerConnectionFactory extends FrontendConnectionFactory {

    @Override
    protected FrontendConnection getConnection() {
        SystemConfig systemConfig = CandiceServer.getInstance().getConfig().getSystem();
        ServerConnection connection = new ServerConnection();
        connection.setQueryHandler(new ServerQueryHandler(connection));
        connection.setTxIsolation(systemConfig.getTxIsolation());
        connection.setSession(new NonBlockingSession(connection));
        return connection;
    }
}
