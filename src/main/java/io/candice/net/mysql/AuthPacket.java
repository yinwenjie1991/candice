package io.candice.net.mysql;

import io.candice.config.Capabilities;
import io.candice.net.connection.BackendConnection;
import io.candice.server.mysql.MySQLMessage;
import io.candice.util.BufferUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-14
 */
public class AuthPacket extends MySQLPacket {

    private static final byte[] FILLER = new byte[23];

    public long clientFlags;
    public long maxPacketSize;
    public int charsetIndex;
    public byte[] extra;// from FILLER(23)
    public String user;
    public byte[] password;
    public String database;

    public void read(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        packetLength = mm.readUB3();
        packetId = mm.read();
        clientFlags = mm.readUB4();
        maxPacketSize = mm.readUB4();
        charsetIndex = (mm.read() & 0xff);
        // read extra
        int current = mm.position();
        int len = (int) mm.readLength();
        if (len > 0 && len < FILLER.length) {
            byte[] ab = new byte[len];
            System.arraycopy(mm.bytes(), mm.position(), ab, 0, len);
            this.extra = ab;
        }
        mm.position(current + FILLER.length);
        user = mm.readStringWithNull(); //用户名
        password = mm.readBytesWithLength();   //挑战认证数据
        if (((clientFlags & Capabilities.CLIENT_CONNECT_WITH_DB) != 0) && mm.hasRemaining()) {
            database = mm.readStringWithNull();
        }
    }

    @Override
    public void write(ChannelHandlerContext context) {
        ByteBuf buf = context.alloc().buffer();
        BufferUtil.writeUB3(buf, calcPacketSize());
        buf.writeByte(packetId);
        BufferUtil.writeUB4(buf, clientFlags);
        BufferUtil.writeUB4(buf, maxPacketSize);
        buf.writeByte((byte) charsetIndex);
        buf.writeBytes(FILLER);
        if (user == null) {
            buf.writeByte((byte) 0);
        } else {
            byte[] userData = user.getBytes();
            BufferUtil.writeWithNull(buf, userData);
        }
        if (password == null) {
            buf.writeByte((byte) 0);
        } else {
            BufferUtil.writeWithLength(buf, password);
        }
        if (database == null) {
            buf.writeByte((byte) 0);
        } else {
            byte[] databaseData = database.getBytes();
            BufferUtil.writeWithNull(buf, databaseData);
        }
        context.writeAndFlush(buf);
    }

    public int calcPacketSize() {
        int size = 32;// 4+4+1+23;
        size += (user == null) ? 1 : user.length() + 1;
        size += (password == null) ? 1 : BufferUtil.getLength(password);
        size += (database == null) ? 1 : database.length() + 1;
        return size;
    }

    protected String getPacketInfo() {
        return "MySQL Authentication Packet";
    }
}
