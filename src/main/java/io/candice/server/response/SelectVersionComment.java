package io.candice.server.response;

import io.candice.config.Fields;
import io.candice.net.connection.ServerConnection;
import io.candice.net.mysql.EOFPacket;
import io.candice.net.mysql.FieldPacket;
import io.candice.net.mysql.ResultSetHeaderPacket;
import io.candice.net.mysql.RowDataPacket;
import io.candice.util.PacketUtil;
import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-17
 */
public class SelectVersionComment {

    private static byte[] VERSION_COMMENT = null;
    private static final int FIELD_COUNT = 1;
    private static final ResultSetHeaderPacket header = PacketUtil.getHeader(FIELD_COUNT);
    private static final FieldPacket[] fields = new FieldPacket[FIELD_COUNT];
    private static final EOFPacket eof = new EOFPacket();

    static {
        try {
            VERSION_COMMENT = "Candice Server-1.0.0 author:yinwenjie email:yinwenjie_1991@163.com".getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int i = 0;
        byte packetId = 0;
        header.packetId = ++packetId;
        fields[i] = PacketUtil.getField("@@VERSION_COMMENT", Fields.FIELD_TYPE_VAR_STRING);
        fields[i++].packetId = ++packetId;
        eof.packetId = ++packetId;
    }

    public static void response(ServerConnection c) {
        ByteBuf buf = c.getChannelHandlerContext().alloc().buffer();

        // write header
        buf = header.writeBuf(buf);

        // write fields
        for (FieldPacket field : fields) {
            buf = field.writeBuf(buf);
        }

        // write eof
        buf = eof.writeBuf(buf);

        // write rows
        byte packetId = eof.packetId;
        RowDataPacket row = new RowDataPacket(FIELD_COUNT);
        row.add(VERSION_COMMENT);
        row.packetId = ++packetId;
        buf = row.writeBuf(buf);

        // write last eof
        EOFPacket lastEof = new EOFPacket();
        lastEof.packetId = ++packetId;
        buf = lastEof.writeBuf(buf);

        // post write
        c.writeByteBuf(buf);
    }
}
