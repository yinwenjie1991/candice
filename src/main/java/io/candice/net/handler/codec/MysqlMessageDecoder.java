package io.candice.net.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-11
 */
public class MysqlMessageDecoder extends ByteToMessageDecoder{

    private static final int packetHeaderSize = 4;

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 消息长度
        int packetLength = getPacketLength(in);
        if ( packetLength == -1) {
            return ;
        }
        // 序号
        in.readByte();
        if (in.readableBytes() < packetLength) {
            //read 位置rest
            in.resetReaderIndex();
            return;
        }
        in.resetReaderIndex();
        byte[] bytes = new byte[packetLength + packetHeaderSize];
        in.readBytes(bytes);
        in.discardReadBytes();
        out.add(bytes);
    }

    public static int getPacketLength(ByteBuf buf) {
        if (buf.readableBytes() <  packetHeaderSize) {
            return -1;
        } else {
            buf.markReaderIndex();
            int length = buf.readByte() & 0xff;
            length |= (buf.readByte() & 0xff) << 8;
            length |= (buf.readByte() & 0xff) << 16;
            return length ;
        }
    }

}
