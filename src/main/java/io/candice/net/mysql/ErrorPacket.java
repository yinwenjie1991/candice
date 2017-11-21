package io.candice.net.mysql;

import io.candice.net.connection.FrontendConnection;
import io.candice.server.mysql.MySQLMessage;
import io.candice.util.BufferUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-15
 */
public class ErrorPacket extends MySQLPacket{
    public static final byte FIELD_COUNT = (byte) 0xff;
    private static final byte SQLSTATE_MARKER = (byte) '#';
    private static final byte[] DEFAULT_SQLSTATE = "HY000".getBytes();

    public byte fieldCount = FIELD_COUNT;
    public int errno;
    public byte mark = SQLSTATE_MARKER;
    public byte[] sqlState = DEFAULT_SQLSTATE;
    public byte[] message;

    public void read(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        packetLength = mm.readUB3();
        packetId = mm.read();
        fieldCount = mm.read();
        errno = mm.readUB2();
        if (mm.hasRemaining() && (mm.read(mm.position()) == SQLSTATE_MARKER)) {
            mm.read();
            sqlState = mm.readBytes(5);
        }
        message = mm.readBytes();
    }

    @Override
    public ByteBuf writeBuf(ByteBuf buffer) {
        int size = calcPacketSize();
        BufferUtil.writeUB3(buffer, size);
        buffer.writeByte(packetId);
        buffer.writeByte(fieldCount);
        BufferUtil.writeUB2(buffer, errno);
        buffer.writeByte(mark);
        buffer.writeBytes(sqlState);
        if (message != null) {
            buffer.writeBytes(message);
        }
        return buffer;
    }

    public void write(ChannelHandlerContext context) {
        ByteBuf buf = context.alloc().buffer();
        BufferUtil.writeUB3(buf, calcPacketSize());
        buf.writeByte(packetId);
        buf.writeByte(fieldCount);
        BufferUtil.writeUB2(buf, errno);
        buf.writeByte(mark);
        buf.writeBytes(sqlState);
        if (message != null) {
            buf.writeBytes(message);
        }
        context.writeAndFlush(buf);
    }

    @Override
    public int calcPacketSize() {
        int size = 9;// 1 + 2 + 1 + 5
        if (message != null) {
            size += message.length;
        }
        return size;
    }

    @Override
    protected String getPacketInfo() {
        return "MySQL Error Packet";
    }

}
