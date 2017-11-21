package io.candice.test;

import io.candice.net.handler.codec.MysqlMessageDecoder;
import io.netty.buffer.Unpooled;
import org.junit.Test;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-13
 */
public class TestMySQLPacketLength {

    @Test
    public void testLength() {
        byte[] QUIT = new byte[] { 1, 0, 0, 0, 1 };
        System.out.println("length is : " + MysqlMessageDecoder.getPacketLength(Unpooled.copiedBuffer(QUIT)));
    }
}
