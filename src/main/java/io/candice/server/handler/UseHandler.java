package io.candice.server.handler;

import io.candice.config.ErrorCode;
import io.candice.net.connection.ServerConnection;
import io.candice.net.handler.FrontendPrivileges;
import io.candice.net.mysql.OkPacket;

import java.util.Set;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-20
 */
public class UseHandler {

    public static void handle(String sql, ServerConnection c, int offset) {
        String schema = sql.substring(offset).trim();
        int length = schema.length();
        if (length > 0) {
            if (schema.charAt(0) == '`' && schema.charAt(length - 1) == '`') {
                schema = schema.substring(1, length - 2);
            }
        }

        // 表示当前连接已经指定了schema
        if (c.getSchema() != null) {
            if (c.getSchema().equals(schema)) {
                c.write(OkPacket.OK);
            } else {
                c.writeErrMessage(ErrorCode.ER_DBACCESS_DENIED_ERROR, "Not allowed to change the database!");
            }
            return;
        }

        // 检查schema的有效性
        FrontendPrivileges privileges = c.getPrivileges();
        if (schema == null || !privileges.schemaExists(schema)) {
            c.writeErrMessage(ErrorCode.ER_BAD_DB_ERROR, "Unknown database '" + schema + "'");
            return;
        }
        String user = c.getUser();
        if (!privileges.userExists(user, c.getHost())) {
            c.writeErrMessage(ErrorCode.ER_ACCESS_DENIED_ERROR, "Access denied for user '" + c.getUser() + "'");
            return;
        }
        Set<String> schemas = privileges.getUserSchemas(user);
        if (schemas == null || schemas.size() == 0 || schemas.contains(schema)) {
            c.setSchema(schema);
            c.write(OkPacket.OK);
        } else {
            String msg = "Access denied for user '" + c.getUser() + "' to database '" + schema + "'";
            c.writeErrMessage(ErrorCode.ER_DBACCESS_DENIED_ERROR, msg);
        }
    }

}
