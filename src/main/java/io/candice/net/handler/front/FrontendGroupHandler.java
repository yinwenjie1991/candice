package io.candice.net.handler.front;

import io.candice.net.connection.FrontendConnection;
import io.candice.net.connection.impl.ServerConnection;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件描述:idlehandler 连接管理、超时检查
 * 作者: yinwenjie
 * 日期: 2017-10-10
 */
public class FrontendIdleHandler extends ChannelInboundHandlerAdapter {

    public static ConcurrentHashMap<String, ServerConnection> GROUP_MAP = new ConcurrentHashMap<String, ServerConnection>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        super.channelActive(ctx);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }
}
