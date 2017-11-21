package io.candice.net.mysql;

import io.candice.server.mysql.MySQLMessage;
import io.candice.util.BufferUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.nio.ByteBuffer;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-16
 */
public class OkPacket extends MySQLPacket {
    public static final byte FIELD_COUNT = 0x00;
    public static final byte[] OK = new byte[] { 7, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0 };

    public byte fieldCount = FIELD_COUNT;
    public long affectedRows;
    public long insertId;
    public int serverStatus;
    public int warningCount;
    public byte[] message;

//    public void read(BinaryPacket bin) {
//        packetLength = bin.packetLength;
//        packetId = bin.packetId;
//        MySQLMessage mm = new MySQLMessage(bin.data);
//        fieldCount = mm.read();
//        affectedRows = mm.readLength();
//        insertId = mm.readLength();
//        serverStatus = mm.readUB2();
//        warningCount = mm.readUB2();
//        if (mm.hasRemaining()) {
//            this.message = mm.readBytesWithLength();
//        }
//    }

    public void read(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        packetLength = mm.readUB3();
        packetId = mm.read();
        fieldCount = mm.read();
        affectedRows = mm.readLength();
        insertId = mm.readLength();
        serverStatus = mm.readUB2();
        warningCount = mm.readUB2();
        if (mm.hasRemaining()) {
            this.message = mm.readBytesWithLength();
        }
    }

    public void write(ChannelHandlerContext context) {
        ByteBuf buf = context.alloc().buffer();
        BufferUtil.writeUB3(buf, calcPacketSize());
        buf.writeByte(packetId);
        buf.writeByte(fieldCount);
        BufferUtil.writeLength(buf, affectedRows);
        BufferUtil.writeLength(buf, insertId);
        BufferUtil.writeUB2(buf, serverStatus);
        BufferUtil.writeUB2(buf, warningCount);
        if (message != null) {
            BufferUtil.writeWithLength(buf, message);
        }
        context.writeAndFlush(buf);
    }

    @Override
    public int calcPacketSize() {
        int i = 1;
        i += BufferUtil.getLength(affectedRows);
        i += BufferUtil.getLength(insertId);
        i += 4;
        if (message != null) {
            i += BufferUtil.getLength(message);
        }
        return i;
    }

    @Override
    protected String getPacketInfo() {
        return "MySQL OK Packet";
    }

}
