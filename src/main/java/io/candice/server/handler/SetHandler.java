package io.candice.server.handler;

import io.candice.config.ErrorCode;
import io.candice.config.Isolations;
import io.candice.net.connection.ServerConnection;
import io.candice.net.mysql.OkPacket;
import io.candice.server.parser.ServerParseSet;
import io.candice.server.response.CharacterSet;
import org.apache.log4j.Logger;

import static io.candice.server.parser.ServerParseSet.*;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-10
 */
public class SetHandler {

    private static final Logger logger = Logger.getLogger(SetHandler.class);
    private static final byte[] AC_OFF = new byte[] { 7, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 };

    public static void handle(String stmt, ServerConnection c, int offset) {
        int rs = ServerParseSet.parse(stmt, offset);
        switch (rs & 0xff) {
            case AUTOCOMMIT_ON:
                if (c.isAutocommit()) {
                    c.write(OkPacket.OK);
                } else {
                    c.commit();
                    c.setAutocommit(true);
                }
                break;
            case AUTOCOMMIT_OFF: {
                if (c.isAutocommit()) {
                    c.setAutocommit(false);
                }
                c.write(AC_OFF);
                break;
            }
            case TX_READ_UNCOMMITTED: {
                c.setTxIsolation(Isolations.READ_UNCOMMITTED);
                c.write(OkPacket.OK);
                break;
            }
            case TX_READ_COMMITTED: {
                c.setTxIsolation(Isolations.READ_COMMITTED);
                c.write(OkPacket.OK);
                break;
            }
            case TX_REPEATED_READ: {
                c.setTxIsolation(Isolations.REPEATED_READ);
                c.write(OkPacket.OK);
                break;
            }
            case TX_SERIALIZABLE: {
                c.setTxIsolation(Isolations.SERIALIZABLE);
                c.write(OkPacket.OK);
                break;
            }
            case NAMES:
                String charset = stmt.substring(rs >>> 8).trim();
                if (c.setCharset(charset)) {
                    c.write(OkPacket.OK);
                } else {
                    c.writeErrMessage(ErrorCode.ER_UNKNOWN_CHARACTER_SET, "Unknown charset '" + charset + "'");
                }
                break;
            case CHARACTER_SET_CLIENT:
            case CHARACTER_SET_CONNECTION:
            case CHARACTER_SET_RESULTS:
                CharacterSet.response(stmt, c, rs);
                break;
            default:
                StringBuilder s = new StringBuilder();
                logger.warn(s.append(c).append(stmt).append(" is not executed").toString());
                c.write(OkPacket.OK);
        }
    }
}
