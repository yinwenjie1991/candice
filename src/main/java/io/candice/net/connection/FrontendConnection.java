package io.candice.net.connection;

import io.candice.config.ErrorCode;
import io.candice.net.handler.FrontendPrivileges;
import io.candice.net.mysql.ErrorPacket;
import io.candice.net.mysql.OkPacket;
import io.candice.server.handler.FrontendPrepareHandler;
import io.candice.server.handler.FrontendQueryHandler;
import io.candice.server.mysql.MySQLMessage;
import io.candice.util.CharsetUtil;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Set;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-10
 */
public class FrontendConnection extends AbstractConnection {

    private static final Logger LOGGER   = Logger.getLogger(FrontendConnection.class);

    protected long id;
    protected String host;
    protected int port;
    protected int localPort;
    protected String user;
    protected String schema;
    protected FrontendPrivileges privileges;
    protected byte[] seed;
    protected String charset;
    protected int charsetIndex;
    protected FrontendQueryHandler queryHandler;
    protected FrontendPrepareHandler prepareHandler;
    protected boolean isAuthenticated;

    public void writeErrMessage(int errno, String msg) {
        writeErrMessage((byte) 1, errno, msg);
    }

    public void writeErrMessage(byte id, int errno, String msg) {
        ErrorPacket err = new ErrorPacket();
        err.packetId = id;
        err.errno = errno;
        err.message = encodeString(msg, charset);
        err.write(this.channelHandlerContext);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public byte[] getSeed() {
        return seed;
    }

    public void setSeed(byte[] seed) {
        this.seed = seed;
    }

    public int getCharsetIndex() {
        return charsetIndex;
    }

    public boolean setCharsetIndex(int ci) {
        String charset = CharsetUtil.getCharset(ci);
        if (charset != null) {
            this.charset = charset;
            this.charsetIndex = ci;
            return true;
        } else {
            return false;
        }
    }

    public String getCharset() {
        return charset;
    }

    public boolean setCharset(String charset) {
        int ci = CharsetUtil.getIndex(charset);
        if (ci > 0) {
            this.charset = charset;
            this.charsetIndex = ci;
            return true;
        } else {
            return false;
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getLocalPort() {
        return localPort;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public FrontendPrivileges getPrivileges() {
        return privileges;
    }

    public void setPrivileges(FrontendPrivileges privileges) {
        this.privileges = privileges;
    }

    public String getUser() {
        return user;
    }

    public String getSchema() {
        return schema;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public void setQueryHandler(FrontendQueryHandler queryHandler) {
        this.queryHandler = queryHandler;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("[thread=").append(Thread.currentThread().getName()).append(",class=")
                .append(getClass().getSimpleName()).append(",host=").append(host).append(",port=").append(port)
                .append(",schema=").append(schema).append(']').toString();
    }

    private final static byte[] encodeString(String src, String charset) {
        if (src == null) {
            return null;
        }
        if (charset == null) {
            return src.getBytes();
        }
        try {
            return src.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            return src.getBytes();
        }
    }

    public void initDB(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        mm.position(5);
        String db = mm.readString();

        // 检查schema是否已经设置
        if (schema != null) {
            if (schema.equals(db)) {
                write(OkPacket.OK);
            } else {
                writeErrMessage(ErrorCode.ER_DBACCESS_DENIED_ERROR, "Not allowed to change the database!");
            }
            return;
        }

        // 检查schema的有效性
        if (db == null || !privileges.schemaExists(db)) {
            writeErrMessage(ErrorCode.ER_BAD_DB_ERROR, "Unknown database '" + db + "'");
            return;
        }
        if (!privileges.userExists(user, host)) {
            writeErrMessage(ErrorCode.ER_ACCESS_DENIED_ERROR, "Access denied for user '" + user + "'");
            return;
        }
        Set<String> schemas = privileges.getUserSchemas(user);
        if (schemas == null || schemas.size() == 0 || schemas.contains(db)) {
            this.schema = db;
            write(OkPacket.OK);
        } else {
            String s = "Access denied for user '" + user + "' to database '" + db + "'";
            writeErrMessage(ErrorCode.ER_DBACCESS_DENIED_ERROR, s);
        }
    }

    public void query(byte[] data) {
        if (queryHandler != null) {
            // 取得语句
            MySQLMessage mm = new MySQLMessage(data);
            mm.position(5);
            String sql = null;
            try {
                sql = mm.readString(charset);
            } catch (UnsupportedEncodingException e) {
                writeErrMessage(ErrorCode.ER_UNKNOWN_CHARACTER_SET, "Unknown charset '" + charset + "'");
                return;
            }
            if (sql == null || sql.length() == 0) {
                writeErrMessage(ErrorCode.ER_NOT_ALLOWED_COMMAND, "Empty SQL");
                return;
            }

            // 执行查询
            queryHandler.query(sql);
        } else {
            writeErrMessage(ErrorCode.ER_UNKNOWN_COM_ERROR, "Query unsupported!");
        }
    }

    public void ping() {
        write(OkPacket.OK);
    }

//    public void close() {
//        LOGGER.info("FrontendConnection: " + host + ":" + port + " close");
//        channelHandlerContext.close();
//    }

    public void kill(byte[] data) {
        writeErrMessage(ErrorCode.ER_UNKNOWN_COM_ERROR, "Unknown command");
    }

    public void heartbeat(byte[] data) {
        write(OkPacket.OK);
    }

    public void stmtPrepare(byte[] data) {

        // 目前不支持mysql预处理
        writeErrMessage(ErrorCode.ER_UNKNOWN_COM_ERROR, "Prepare unsupported!");

    }

    public void stmtExecute(byte[] data) {

        writeErrMessage(ErrorCode.ER_UNKNOWN_COM_ERROR, "Prepare unsupported!");

    }

    public void stmtClose(byte[] data) {

        writeErrMessage(ErrorCode.ER_UNKNOWN_COM_ERROR, "Prepare unsupported!");
    }
}
