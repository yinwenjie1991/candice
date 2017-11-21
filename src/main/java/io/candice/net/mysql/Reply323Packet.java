package io.candice.net.mysql;

import io.candice.util.BufferUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.nio.ByteBuffer;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-17
 */
public class Reply323Packet extends MySQLPacket {

    public byte[] seed;

    @Override
    public void write(ChannelHandlerContext context) {
        ByteBuf buf = context.alloc().buffer();
        BufferUtil.writeUB3(buf, calcPacketSize());
        buf.writeByte(packetId);
        if (seed == null) {
            buf.writeByte((byte) 0);
        } else {
            BufferUtil.writeWithNull(buf, seed);
        }
        context.writeAndFlush(buf);
    }

    @Override
    public int calcPacketSize() {
        return seed == null ? 1 : seed.length + 1;
    }

    @Override
    protected String getPacketInfo() {
        return "MySQL Auth323 Packet";
    }
}
