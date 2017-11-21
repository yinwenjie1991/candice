package io.candice.server.response;

import io.candice.config.Fields;
import io.candice.net.connection.ServerConnection;
import io.candice.net.mysql.EOFPacket;
import io.candice.net.mysql.FieldPacket;
import io.candice.net.mysql.ResultSetHeaderPacket;
import io.candice.net.mysql.RowDataPacket;
import io.candice.util.PacketUtil;
import io.candice.util.StringUtil;
import io.netty.buffer.ByteBuf;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-18
 */
public class SelectDatabase {

    private static final int FIELD_COUNT = 1;
    private static final ResultSetHeaderPacket header = PacketUtil.getHeader(FIELD_COUNT);
    private static final FieldPacket[] fields = new FieldPacket[FIELD_COUNT];
    private static final EOFPacket eof = new EOFPacket();
    static {
        int i = 0;
        byte packetId = 0;
        header.packetId = ++packetId;
        fields[i] = PacketUtil.getField("DATABASE()", Fields.FIELD_TYPE_VAR_STRING);
        fields[i++].packetId = ++packetId;
        eof.packetId = ++packetId;
    }

    public static void response(ServerConnection c) {
        ByteBuf buf = c.getChannelHandlerContext().alloc().buffer();
        buf = header.writeBuf(buf);
        for (FieldPacket field : fields) {
            buf = field.writeBuf(buf);
        }
        buf = eof.writeBuf(buf);
        byte packetId = eof.packetId;
        RowDataPacket row = new RowDataPacket(FIELD_COUNT);
        row.add(StringUtil.encode(c.getSchema(), c.getCharset()));
        row.packetId = ++packetId;
        buf = row.writeBuf(buf);
        EOFPacket lastEof = new EOFPacket();
        lastEof.packetId = ++packetId;
        buf = lastEof.writeBuf(buf);
        c.getChannelHandlerContext().writeAndFlush(buf);
    }

}
