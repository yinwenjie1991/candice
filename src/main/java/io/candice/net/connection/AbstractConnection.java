package io.candice.net;

import io.netty.channel.ChannelHandlerContext;

/**
 * 文件描述:连接抽象类，对netty中的context进行一层封装
 * 作者: yinwenjie
 * 日期: 2017-10-09
 */
public abstract class AbstractConnection {

    private ChannelHandlerContext channelHandlerContext;

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }
}
