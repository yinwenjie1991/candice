package io.candice.net.handler.backend;

import io.candice.net.connection.MySQLConnection;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-16
 */
public class BackendTailHandler extends ChannelInboundHandlerAdapter{

    private MySQLConnection source;

    public BackendTailHandler(MySQLConnection source) {
        this.source = source;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        source.setIsQuit(true);
        source.setRunning(false);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        source.setIsQuit(true);
        source.setRunning(false);
        ctx.close();
        super.exceptionCaught(ctx, cause);
    }
}
