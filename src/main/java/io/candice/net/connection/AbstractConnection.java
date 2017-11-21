package io.candice.net.connection;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 文件描述:连接抽象类，对netty中的context进行一层封装
 * 作者: yinwenjie
 * 日期: 2017-10-09
 */
public abstract class AbstractConnection {

    protected AtomicBoolean isClosed = new AtomicBoolean(false);

    protected ChannelHandlerContext channelHandlerContext;

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    public void write(byte[] bytes) {
        ByteBuf buf = channelHandlerContext.alloc().buffer(bytes.length);
        buf.writeBytes(bytes);
        channelHandlerContext.writeAndFlush(buf);
    }

    public void writeByteBuf(ByteBuf buf) {
        channelHandlerContext.writeAndFlush(buf);
    }

    public boolean close() {
        if (isClosed.get()) {
            return false;
        } else {
            if (channelHandlerContext != null ) {
                channelHandlerContext.close();
                return isClosed.compareAndSet(false, true);
            } else {
                return false;
            }
        }
    }

    public boolean isClosed() {
        //检查是否关闭
        if ( channelHandlerContext != null ) {
            if (channelHandlerContext.channel() != null) {
                isClosed.set(!channelHandlerContext.channel().isOpen());
                return isClosed.get();
            } else {
                isClosed.set(true);
                return isClosed.get();
            }
        }
        return isClosed.get();

    }
}
