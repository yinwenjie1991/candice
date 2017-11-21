package io.candice.net.mysql;

import io.candice.server.mysql.MySQLMessage;
import io.candice.util.BufferUtil;
import io.netty.buffer.ByteBuf;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-18
 */
public class ResultSetHeaderPacket extends MySQLPacket{

    /**
     * field取值范围 0x00-0xFA(250)
     */
    public int fieldCount;
    public long extra;

    public void read(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        this.packetLength = mm.readUB3();
        this.packetId = mm.read();
        this.fieldCount = (int) mm.readLength();
        if (mm.hasRemaining()) {
            this.extra = mm.readLength();
        }
    }

    @Override
    public ByteBuf writeBuf(ByteBuf buffer) {
        int size = calcPacketSize();
        BufferUtil.writeUB3(buffer, size);
        buffer.writeByte(packetId);
        BufferUtil.writeLength(buffer, fieldCount);
        if (extra > 0) {
            BufferUtil.writeLength(buffer, extra);
        }
        return buffer;
    }

    @Override
    public int calcPacketSize() {
        int size = BufferUtil.getLength(fieldCount);
        if (extra > 0) {
            size += BufferUtil.getLength(extra);
        }
        return size;
    }

    @Override
    protected String getPacketInfo() {
        return "MySQL ResultSetHeader Packet";
    }
}
