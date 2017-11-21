package io.candice.net.mysql;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-17
 */
public class FieldPacket extends MySQLPacket {

    public int calcPacketSize() {
        return 0;
    }

    protected String getPacketInfo() {
        return null;
    }
}
