package io.candice.util;

import io.netty.buffer.ByteBuf;

/**
 * 文件描述:netty buffer工具，用户满足mysql传输协议
 * 作者: yinwenjie
 * 日期: 2017-10-12
 */
public class BufferUtil {

    public static final void writeUB2(ByteBuf buf, int i) {
        buf.writeByte((byte) (i & 0xff));
        buf.writeByte((byte) (i >>> 8));
    }

    public static final void writeUB3(ByteBuf buffer, int i) {
        buffer.writeByte((byte) (i & 0xff));
        buffer.writeByte((byte) (i >>> 8));
        buffer.writeByte((byte) (i >>> 16));
    }
}
