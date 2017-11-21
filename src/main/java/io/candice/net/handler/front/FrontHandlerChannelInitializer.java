package io.candice.net.handler.front;

import io.candice.net.connection.FrontendConnection;
import io.candice.net.factory.FrontendConnectionFactory;
import io.candice.net.handler.codec.MysqlMessageDecoder;
import io.candice.net.handler.enums.FrontHandlerNameEnum;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-04
 */
public class FrontHandlerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private FrontendConnectionFactory factory;

    public FrontHandlerChannelInitializer(FrontendConnectionFactory factory) {
        this.factory = factory;
    }

    private int idleTimeout = 10 * 60; //10min

    protected void initChannel(SocketChannel ch) throws Exception {
        //1. 超时检查的handler
        FrontendConnection frontendConnection = factory.make();
        ch.pipeline().addLast( FrontHandlerNameEnum.IDLE.getCode()
                , new IdleStateHandler(0, 0, idleTimeout));
        ch.pipeline().addLast( FrontHandlerNameEnum.GROUP.getCode(), new FrontendGroupHandler(frontendConnection));
        //2. 解码器
        ch.pipeline().addLast( FrontHandlerNameEnum.MYSQL_DECODER.getCode(), new MysqlMessageDecoder());
        //3. 处理器
        ch.pipeline().addLast( FrontHandlerNameEnum.AUTH.getCode(), new FrontendAuthenticatorHandler(frontendConnection));
    }
}
