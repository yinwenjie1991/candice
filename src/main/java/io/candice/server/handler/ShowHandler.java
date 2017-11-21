package io.candice.server.handler;

import io.candice.net.connection.ServerConnection;
import io.candice.net.mysql.OkPacket;
import io.candice.server.parser.ServerParse;
import io.candice.server.parser.ServerParseShow;
import io.candice.server.response.ShowDatabases;

public final class ShowHandler {

    public static void handle(String stmt, ServerConnection c, int offset) {
        switch (ServerParseShow.parse(stmt, offset)) {
        case ServerParseShow.DATABASES:
            ShowDatabases.response(c);
            break;
        case ServerParseShow.DATASOURCES:
        case ServerParseShow.COBAR_STATUS:
        case ServerParseShow.COBAR_CLUSTER:
            // 目前不做处理
            c.write(OkPacket.OK);

            break;
        default:
            c.execute(stmt, ServerParse.SHOW);
        }
    }

}
