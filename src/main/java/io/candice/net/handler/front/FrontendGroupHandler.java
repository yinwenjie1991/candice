package io.candice.net.handler.front;

import io.candice.net.connection.FrontendConnection;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件描述:FrontendGroupHandler 连接管理、超时检查
 * 作者: yinwenjie
 * 日期: 2017-10-10
 */
public class FrontendGroupHandler extends ChannelInboundHandlerAdapter {

    public static ConcurrentHashMap<Long, FrontendConnection> GROUP_MAP = new ConcurrentHashMap<Long, FrontendConnection>();

    private FrontendConnection frontendConnection;

    public FrontendGroupHandler(FrontendConnection serverConnection) {
        this.frontendConnection = serverConnection;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        frontendConnection.setChannelHandlerContext(ctx);

        GROUP_MAP.put(frontendConnection.getId(), frontendConnection);
        super.channelActive(ctx);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        if (this)
//        GROUP_MAP.remove()
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //1. 关闭连接
        ctx.close();
//        super.userEventTriggered(ctx, evt);
        //2. map remove
        GROUP_MAP.remove(frontendConnection.getId());
    }
}
