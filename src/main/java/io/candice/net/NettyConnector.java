package io.candice.net;

import io.candice.config.CandiceConfig;
import io.candice.config.SocketConfig;
import io.candice.net.handler.backend.BackendHandlerChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.log4j.Logger;

/**
 * 文件描述:后端客户连接
 * 作者: yinwenjie
 * 日期: 2017-10-31
 */
public class NettyConnector {

    private static final Logger LOGGER = Logger.getLogger(NettyConnector.class);

    private final String name;

    private final int processors;

    private final EventLoopGroup group;

    private Bootstrap b;

    public NettyConnector(String name, CandiceConfig config) {
        this.name = name;
        this.processors = config.getSystem().getProcessors();
        this.group = new NioEventLoopGroup(processors);
    }

    public void config() {
        try {
            b = new Bootstrap();

            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new BackendHandlerChannelInitializer());


        } catch (Exception e) {
            LOGGER.error("NettyConnector config exception: ", e);

        }
    }

    private void setOption(Bootstrap bootstrap) {

        bootstrap.option(ChannelOption.SO_RCVBUF, SocketConfig.BACKEND_SO_RCVBUF);
        bootstrap.option(ChannelOption.SO_SNDBUF, SocketConfig.BACKEND_SO_RCVBUF );
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, SocketConfig.CONNECT_TIMEOUT_MILLIS);
        bootstrap.option(ChannelOption.SO_TIMEOUT, SocketConfig.SO_TIMEOUT);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
    }
}
