package io.candice.net.mysql;

import io.candice.server.mysql.MySQLMessage;
import io.candice.util.BufferUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-11
 */
public class HandshakePacket extends  MySQLPacket{

    private static final byte[] FILLER_13 = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    public byte protocolVersion;
    public byte[] serverVersion;
    public long threadId;
    public byte[] seed;
    public int serverCapabilities;
    public byte serverCharsetIndex;
    public int serverStatus;
    public byte[] restOfScrambleBuff;

    public void read(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        packetLength = mm.readUB3();
        packetId = mm.read();
        protocolVersion = mm.read();
        serverVersion = mm.readBytesWithNull();
        threadId = mm.readUB4();
        seed = mm.readBytesWithNull();
        serverCapabilities = mm.readUB2();
        serverCharsetIndex = mm.read();
        serverStatus = mm.readUB2();
        mm.move(13);
        restOfScrambleBuff = mm.readBytesWithNull();
    }

    public void write(ChannelHandlerContext ctx) {
        ByteBuf buf = ctx.alloc().buffer();
        BufferUtil.writeUB3(buf, calcPacketSize());
        buf.writeByte(packetId);
        buf.writeByte(protocolVersion);
        BufferUtil.writeWithNull(buf, serverVersion);
        BufferUtil.writeUB4(buf, threadId);
        BufferUtil.writeWithNull(buf, seed);
        BufferUtil.writeUB2(buf, serverCapabilities);
        buf.writeByte(serverCharsetIndex);
        BufferUtil.writeUB2(buf, serverStatus);
        buf.writeBytes(FILLER_13);
        //        buffer.position(buffer.position() + 13);
        BufferUtil.writeWithNull(buf, restOfScrambleBuff);
        ctx.writeAndFlush(buf);
    }

    @Override
    public int calcPacketSize() {
        int size = 1;
        size += serverVersion.length;// n
        size += 5;// 1+4
        size += seed.length;// 8
        size += 19;// 1+2+1+2+13
        size += restOfScrambleBuff.length;// 12
        size += 1;// 1
        return size;
    }

    @Override
    protected String getPacketInfo() {
        return "MySQL Handshake Packet";
    }
}
