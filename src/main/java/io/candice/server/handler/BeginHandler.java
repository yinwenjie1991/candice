package io.candice.server.handler;

import io.candice.config.ErrorCode;
import io.candice.net.connection.ServerConnection;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-20
 */
public class BeginHandler {

    public static void handle(String stmt, ServerConnection c) {
        c.writeErrMessage(ErrorCode.ER_UNKNOWN_COM_ERROR, "Unsupported statement");
    }

}
