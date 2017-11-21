package io.candice.util;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

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

    public static final void writeUB3(ByteBuf buf, int i) {
        buf.writeByte((byte) (i & 0xff));
        buf.writeByte((byte) (i >>> 8));
        buf.writeByte((byte) (i >>> 16));
    }

    public static final void writeInt(ByteBuf buf, int i) {
        buf.writeByte((byte) (i & 0xff));
        buf.writeByte((byte) (i >>> 8));
        buf.writeByte((byte) (i >>> 16));
        buf.writeByte((byte) (i >>> 24));
    }

    public static final void writeFloat(ByteBuf buf, float f) {
        writeInt(buf, Float.floatToIntBits(f));
    }

    public static final void writeUB4(ByteBuf buf, long l) {
        buf.writeByte((byte) (l & 0xff));
        buf.writeByte((byte) (l >>> 8));
        buf.writeByte((byte) (l >>> 16));
        buf.writeByte((byte) (l >>> 24));
    }

    public static final void writeLong(ByteBuf buf, long l) {
        buf.writeByte((byte) (l & 0xff));
        buf.writeByte((byte) (l >>> 8));
        buf.writeByte((byte) (l >>> 16));
        buf.writeByte((byte) (l >>> 24));
        buf.writeByte((byte) (l >>> 32));
        buf.writeByte((byte) (l >>> 40));
        buf.writeByte((byte) (l >>> 48));
        buf.writeByte((byte) (l >>> 56));
    }

    public static final void writeDouble(ByteBuf buf, double d) {
        writeLong(buf, Double.doubleToLongBits(d));
    }

    public static final void writeLength(ByteBuf buf, long l) {
        if (l < 251) {
            buf.writeByte((byte) l);
        } else if (l < 0x10000L) {
            buf.writeByte((byte) 252);
            writeUB2(buf, (int) l);
        } else if (l < 0x1000000L) {
            buf.writeByte((byte) 253);
            writeUB3(buf, (int) l);
        } else {
            buf.writeByte((byte) 254);
            writeLong(buf, l);
        }
    }

    public static final void writeWithNull(ByteBuf buf, byte[] src) {
        buf.writeBytes(src);
        buf.writeByte((byte) 0);
    }

    public static final void writeWithLength(ByteBuf buf, byte[] src) {
        int length = src.length;
        if (length < 251) {
            buf.writeByte((byte) length);
        } else if (length < 0x10000L) {
            buf.writeByte((byte) 252);
            writeUB2(buf, length);
        } else if (length < 0x1000000L) {
            buf.writeByte((byte) 253);
            writeUB3(buf, length);
        } else {
            buf.writeByte((byte) 254);
            writeLong(buf, length);
        }
        buf.writeBytes(src);
    }

    public static final void writeWithLength(ByteBuf buf, byte[] src, byte nullValue) {
        if (src == null) {
            buf.writeByte(nullValue);
        } else {
            writeWithLength(buf, src);
        }
    }

    public static final int getLength(long length) {
        if (length < 251) {
            return 1;
        } else if (length < 0x10000L) {
            return 3;
        } else if (length < 0x1000000L) {
            return 4;
        } else {
            return 9;
        }
    }

    public static final int getLength(byte[] src) {
        int length = src.length;
        if (length < 251) {
            return 1 + length;
        } else if (length < 0x10000L) {
            return 3 + length;
        } else if (length < 0x1000000L) {
            return 4 + length;
        } else {
            return 9 + length;
        }
    }
}
