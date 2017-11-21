package io.candice.server.handler;

import io.candice.config.ErrorCode;
import io.candice.net.connection.ServerConnection;
import io.candice.server.parser.ServerParse;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-20
 */
public class StartHandler {

    public static void handle(String stmt, ServerConnection c, int offset) {
        switch (ServerParseStart.parse(stmt, offset)) {
            case ServerParseStart.TRANSACTION:
                c.writeErrMessage(ErrorCode.ER_UNKNOWN_COM_ERROR, "Unsupported statement");
                break;
            default:
                c.execute(stmt, ServerParse.START);
        }
    }

}
