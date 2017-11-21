package io.candice.net.handler.backend;

import io.candice.net.connection.MySQLConnection;
import io.candice.net.factory.MySQLConnectionFactory;
import io.candice.net.handler.codec.MysqlMessageDecoder;
import io.candice.net.handler.enums.BackendHandlerNameEnum;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;


/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-31
 */
public class BackendHandlerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private MySQLConnectionFactory factory;

    public BackendHandlerChannelInitializer(MySQLConnectionFactory factory) {
        this.factory = factory;
    }

    protected void initChannel(SocketChannel ch) throws Exception {

        MySQLConnection mySQLConnection = factory.make();
        BackendHeadHandler backendHeadHandler = new BackendHeadHandler(mySQLConnection);
        BackendMySQLAuthenticatorHandler backendMySQLAuthenticatorHandler =
                new BackendMySQLAuthenticatorHandler(mySQLConnection);
        BackendTailHandler backendTailHandler = new BackendTailHandler(mySQLConnection);

        ch.pipeline().addLast(BackendHandlerNameEnum.MYSQL_DECODER.getCode(), new MysqlMessageDecoder());
        ch.pipeline().addLast(BackendHandlerNameEnum.HEAD.getCode(), backendHeadHandler);
        ch.pipeline().addLast(BackendHandlerNameEnum.MYSQL_AUTH.getCode(), backendMySQLAuthenticatorHandler);
        ch.pipeline().addLast(BackendHandlerNameEnum.TAIL.getCode(), backendTailHandler);

    }
}
