package io.candice.net.handler.backend;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-17
 */
public class BackendMySQLConnectionHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }
}
