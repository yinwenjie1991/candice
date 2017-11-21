package io.candice.net.mysql;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-13
 */
public class QuitPacket extends MySQLPacket {
    public static final byte[] QUIT = new byte[] { 1, 0, 0, 0, 1 };

    public int calcPacketSize() {
        return 1;
    }

    protected String getPacketInfo() {
        return "MySQL Quit Packet";
    }
}
