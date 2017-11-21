package io.candice.server.response;

import io.candice.CandiceServer;
import io.candice.config.CandiceConfig;
import io.candice.config.Fields;
import io.candice.config.model.UserConfig;
import io.candice.net.connection.ServerConnection;
import io.candice.net.mysql.EOFPacket;
import io.candice.net.mysql.FieldPacket;
import io.candice.net.mysql.ResultSetHeaderPacket;
import io.candice.net.mysql.RowDataPacket;
import io.candice.util.PacketUtil;
import io.candice.util.StringUtil;
import io.netty.buffer.ByteBuf;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ShowDatabases {

    private static final int FIELD_COUNT = 1;
    private static final ResultSetHeaderPacket header = PacketUtil.getHeader(FIELD_COUNT);
    private static final FieldPacket[] fields = new FieldPacket[FIELD_COUNT];
    private static final EOFPacket eof = new EOFPacket();
    static {
        int i = 0;
        byte packetId = 0;
        header.packetId = ++packetId;
        fields[i] = PacketUtil.getField("DATABASE", Fields.FIELD_TYPE_VAR_STRING);
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
        CandiceConfig conf = CandiceServer.getInstance().getConfig();
        Map<String, UserConfig> users = conf.getUsers();
        UserConfig user = users == null ? null : users.get(c.getUser());
        if (user != null) {
            TreeSet<String> schemaSet = new TreeSet<String>();
            Set<String> schemaList = user.getSchemas();
            if (schemaList == null || schemaList.size() == 0) {
                schemaSet.addAll(conf.getSchemas().keySet());
            } else {
                for (String schema : schemaList) {
                    schemaSet.add(schema);
                }
            }
            for (String name : schemaSet) {
                RowDataPacket row = new RowDataPacket(FIELD_COUNT);
                row.add(StringUtil.encode(name, c.getCharset()));
                row.packetId = ++packetId;
                buf = row.writeBuf(buf);
            }
        }

        // write last eof
        EOFPacket lastEof = new EOFPacket();
        lastEof.packetId = ++packetId;
        buf = lastEof.writeBuf(buf);

        // post write
        c.writeByteBuf(buf);
    }

}
