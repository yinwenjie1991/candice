package io.candice.net.mysql;

import io.netty.channel.ChannelHandlerContext;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-17
 */
public class Reply323Packet extends MySQLPacket {

    public byte[] seed;

    @Override
    public void write(ChannelHandlerContext context) {

    }

    @Override
    public int calcPacketSize() {
        return seed == null ? 1 : seed.length + 1;
    }

    @Override
    protected String getPacketInfo() {
        return "MySQL Auth323 Packet";
    }
}
