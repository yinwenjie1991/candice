package io.candice.net;

import io.candice.config.CandiceConfig;
import io.candice.config.SocketConfig;
import io.candice.net.factory.ServerConnectionFactory;
import io.candice.net.handler.front.FrontHandlerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;

/**
 * 文件描述:netty前端接收线程
 * 作者: yinwenjie
 * 日期: 2017-09-26
 */
public class NettyAcceptor extends Thread{

    private static final Logger LOGGER = Logger.getLogger(NettyAcceptor.class);

    private final int port;
    private final int processors;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private final ServerBootstrap serverBootstrap;

    public NettyAcceptor(String name, int port, CandiceConfig config) {
        super.setName(name);
        this.port = port;
        this.processors = config.getSystem().getProcessors();
        this.bossGroup = new NioEventLoopGroup(processors);
        this.workerGroup = new NioEventLoopGroup(processors);
        this.serverBootstrap = new ServerBootstrap();
    }

    @Override
    public void run() {
        try {
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(
                        new FrontHandlerChannelInitializer(new ServerConnectionFactory())
                    )
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, SocketConfig.CONNECT_TIMEOUT_MILLIS)
                    .option(ChannelOption.SO_TIMEOUT, SocketConfig.SO_TIMEOUT);
            ChannelFuture f = serverBootstrap.bind(this.port).sync();
            f.channel().closeFuture().sync();

        } catch (Exception e) {

            LOGGER.error("netty serverBootstrap start error, " , e);

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
