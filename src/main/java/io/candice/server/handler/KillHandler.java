package io.candice.server.handler;

import io.candice.config.ErrorCode;
import io.candice.net.connection.FrontendConnection;
import io.candice.net.connection.ServerConnection;
import io.candice.net.handler.front.FrontendGroupHandler;
import io.candice.net.mysql.OkPacket;
import io.candice.util.StringUtil;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-20
 */
public class KillHandler {

    public static void handle(String stmt, int offset, ServerConnection c) {
        String id = stmt.substring(offset).trim();
        if (StringUtil.isEmpty(id)) {
            c.writeErrMessage(ErrorCode.ER_NO_SUCH_THREAD, "NULL connection id");
        } else {
            // get value
            long value = 0;
            try {
                value = Long.parseLong(id);
            } catch (NumberFormatException e) {
                c.writeErrMessage(ErrorCode.ER_NO_SUCH_THREAD, "Invalid connection id:" + id);
                return;
            }

            // kill myself
            if (value == c.getId()) {
                getOkPacket().write(c.getChannelHandlerContext());
//                c.write(c.allocate());
                return;
            }

            // get connection and close it
            FrontendConnection fc = null;
            for (FrontendConnection connection : FrontendGroupHandler.GROUP_MAP.values()) {
                if (connection.getId() == value) {
                    fc = connection;
                    break;
                }
            }
            if (fc != null) {
                fc.close();
                getOkPacket().write(c.getChannelHandlerContext());
            } else {
                c.writeErrMessage(ErrorCode.ER_NO_SUCH_THREAD, "Unknown connection id:" + id);
            }
        }
    }

    private static OkPacket getOkPacket() {
        OkPacket packet = new OkPacket();
        packet.packetId = 1;
        packet.affectedRows = 0;
        packet.serverStatus = 2;
        return packet;
    }

}
