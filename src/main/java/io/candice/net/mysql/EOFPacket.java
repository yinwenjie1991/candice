package io.candice.net.mysql;

import io.candice.server.mysql.MySQLMessage;
import io.candice.util.BufferUtil;
import io.netty.buffer.ByteBuf;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-17
 */
public class EOFPacket extends MySQLPacket{
    public static final byte FIELD_COUNT = (byte) 0xfe;

    public byte fieldCount = FIELD_COUNT;
    public int warningCount;
    public int status = 2;

    public void read(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        packetLength = mm.readUB3();
        packetId = mm.read();
        fieldCount = mm.read();
        warningCount = mm.readUB2();
        status = mm.readUB2();
    }

    @Override
    public ByteBuf writeBuf(ByteBuf buffer) {
        int size = calcPacketSize();
        BufferUtil.writeUB3(buffer, size);
        buffer.writeByte(packetId);
        buffer.writeByte(fieldCount);
        BufferUtil.writeUB2(buffer, warningCount);
        BufferUtil.writeUB2(buffer, status);
        return buffer;
    }

    public int calcPacketSize() {
        return 5;
    }

    protected String getPacketInfo() {
        return "MySQL EOF Packet";
    }
}
