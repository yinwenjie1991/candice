package io.candice.server.response;

import io.candice.config.Fields;
import io.candice.net.connection.ServerConnection;
import io.candice.net.mysql.EOFPacket;
import io.candice.net.mysql.FieldPacket;
import io.candice.net.mysql.ResultSetHeaderPacket;
import io.candice.net.mysql.RowDataPacket;
import io.candice.parser.util.ParseUtil;
import io.candice.util.LongUtil;
import io.candice.util.PacketUtil;
import io.netty.buffer.ByteBuf;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-18
 */
public class SelectIdentity {

    private static final int FIELD_COUNT = 1;
    private static final ResultSetHeaderPacket header = PacketUtil.getHeader(FIELD_COUNT);
    static {
        byte packetId = 0;
        header.packetId = ++packetId;
    }

    public static void response(ServerConnection c, String stmt, int aliasIndex, final String orgName) {
        String alias = ParseUtil.parseAlias(stmt, aliasIndex);
        if (alias == null) {
            alias = orgName;
        }

        ByteBuf buf = c.getChannelHandlerContext().alloc().buffer();

        // write header
        buf = header.writeBuf(buf);

        // write fields
        byte packetId = header.packetId;
        FieldPacket field = PacketUtil.getField(alias, orgName, Fields.FIELD_TYPE_LONGLONG);
        field.packetId = ++packetId;
        buf = field.writeBuf(buf);

        // write eof
        EOFPacket eof = new EOFPacket();
        eof.packetId = ++packetId;
        buf = eof.writeBuf(buf);

        // write rows
        RowDataPacket row = new RowDataPacket(FIELD_COUNT);
        row.add(LongUtil.toBytes(c.getLastInsertId()));
        row.packetId = ++packetId;
        buf = row.writeBuf(buf);

        // write last eof
        EOFPacket lastEof = new EOFPacket();
        lastEof.packetId = ++packetId;
        buf = lastEof.writeBuf(buf);

        // post write
        c.getChannelHandlerContext().writeAndFlush(buf);
    }

}
