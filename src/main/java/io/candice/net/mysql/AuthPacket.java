package io.candice.net.mysql;

import io.candice.config.Capabilities;
import io.candice.server.mysql.MySQLMessage;

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

    public int calcPacketSize() {
        return 0;
    }

    protected String getPacketInfo() {
        return null;
    }
}
