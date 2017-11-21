package io.candice.server.handler;

import io.candice.CandiceServer;
import io.candice.config.ErrorCode;
import io.candice.config.Fields;
import io.candice.config.model.SchemaConfig;
import io.candice.net.connection.ServerConnection;
import io.candice.net.mysql.EOFPacket;
import io.candice.net.mysql.FieldPacket;
import io.candice.net.mysql.ResultSetHeaderPacket;
import io.candice.net.mysql.RowDataPacket;
import io.candice.route.CandiceServerRouter;
import io.candice.route.RouteResultset;
import io.candice.route.RouteResultsetNode;
import io.candice.util.PacketUtil;
import io.candice.util.StringUtil;
import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;
import java.sql.SQLNonTransientException;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-17
 */
public class ExplainHandler {

    private static final Logger logger      = Logger.getLogger(ExplainHandler.class);
    private static final RouteResultsetNode[] EMPTY_ARRAY = new RouteResultsetNode[0];
    private static final int                  FIELD_COUNT = 2;
    private static final FieldPacket[]        fields      = new FieldPacket[FIELD_COUNT];
    static {
        fields[0] = PacketUtil.getField("DATA_NODE", Fields.FIELD_TYPE_VAR_STRING);
        fields[1] = PacketUtil.getField("SQL", Fields.FIELD_TYPE_VAR_STRING);
    }

    public static void handle(String stmt, ServerConnection c, int offset) {
        stmt = stmt.substring(offset);

        RouteResultset rrs = getRouteResultset(c, stmt);
        if (rrs == null)
            return;

        ByteBuf buf = c.getChannelHandlerContext().alloc().buffer();

        // write header
        ResultSetHeaderPacket header = PacketUtil.getHeader(FIELD_COUNT);
        byte packetId = header.packetId;
        buf = header.writeBuf(buf);
        // write fields
        for (FieldPacket field : fields) {
            field.packetId = ++packetId;
            buf = field.writeBuf(buf);
        }

        // write eof
        EOFPacket eof = new EOFPacket();
        eof.packetId = ++packetId;
        buf = eof.writeBuf(buf);

        // write rows
        RouteResultsetNode[] rrsn = (rrs != null) ? rrs.getNodes() : EMPTY_ARRAY;
        for (RouteResultsetNode node : rrsn) {
            String[] statement = node.getStatement();
            for (String sqlStmt : statement) {
                RowDataPacket row = getRow(node.getName(), sqlStmt, c.getCharset());
                row.packetId = ++packetId;
                buf = row.writeBuf(buf);
            }
        }

        // write last eof
        EOFPacket lastEof = new EOFPacket();
        lastEof.packetId = ++packetId;
        buf = lastEof.writeBuf(buf);

        // post write
//        c.write(buffer);
        c.getChannelHandlerContext().writeAndFlush(buf);
    }

    private static RowDataPacket getRow(String nodeName, String stmt, String charset) {
        RowDataPacket row = new RowDataPacket(FIELD_COUNT);
        row.add(StringUtil.encode(nodeName, charset));
        row.add(StringUtil.encode(stmt, charset));
        return row;
    }

    private static RouteResultset getRouteResultset(ServerConnection c, String stmt) {
        String db = c.getSchema();
        if (db == null) {
            c.writeErrMessage(ErrorCode.ER_NO_DB_ERROR, "No database selected");
            return null;
        }
        SchemaConfig schema = CandiceServer.getInstance().getConfig().getSchemas().get(db);
        if (schema == null) {
            c.writeErrMessage(ErrorCode.ER_BAD_DB_ERROR, "Unknown database '" + db + "'");
            return null;
        }
        try {
            return CandiceServerRouter.route(schema, stmt, c.getCharset(), c);
        } catch (SQLNonTransientException e) {
            StringBuilder s = new StringBuilder();
            logger.warn(s.append(c).append(stmt).toString(), e);
            String msg = e.getMessage();
            c.writeErrMessage(ErrorCode.ER_PARSE_ERROR, msg == null ? e.getClass().getSimpleName()
                    : msg);
            return null;
        }
    }
}
