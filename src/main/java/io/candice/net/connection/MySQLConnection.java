package io.candice.net.connection;

import io.candice.config.Capabilities;
import io.candice.net.mysql.*;
import io.candice.route.RouteResultsetNode;
import io.candice.server.mysql.handler.ResponseHandler;
import io.candice.server.mysql.nio.MySQLConnectionPool;
import io.candice.util.SecurityUtil;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-03
 */
public class MySQLConnection extends BackendConnection {

    private static final Logger LOGGER = Logger.getLogger(MySQLConnection.class);
    private static final long CLIENT_FLAGS = initClientFlags();

    private static long initClientFlags() {
        int flag = 0;
        flag |= Capabilities.CLIENT_LONG_PASSWORD;
        flag |= Capabilities.CLIENT_FOUND_ROWS;
        flag |= Capabilities.CLIENT_LONG_FLAG;
        flag |= Capabilities.CLIENT_CONNECT_WITH_DB;
        // flag |= Capabilities.CLIENT_NO_SCHEMA;
        // flag |= Capabilities.CLIENT_COMPRESS;
        flag |= Capabilities.CLIENT_ODBC;
        // flag |= Capabilities.CLIENT_LOCAL_FILES;
        flag |= Capabilities.CLIENT_IGNORE_SPACE;
        flag |= Capabilities.CLIENT_PROTOCOL_41;
        flag |= Capabilities.CLIENT_INTERACTIVE;
        // flag |= Capabilities.CLIENT_SSL;
        flag |= Capabilities.CLIENT_IGNORE_SIGPIPE;
        flag |= Capabilities.CLIENT_TRANSACTIONS;
        // flag |= Capabilities.CLIENT_RESERVED;
        flag |= Capabilities.CLIENT_SECURE_CONNECTION;
        // client extension
        // flag |= Capabilities.CLIENT_MULTI_STATEMENTS;
        // flag |= Capabilities.CLIENT_MULTI_RESULTS;
        return flag;
    }

    private static final CommandPacket _READ_UNCOMMITTED = new CommandPacket();
    private static final CommandPacket _READ_COMMITTED = new CommandPacket();
    private static final CommandPacket _REPEATED_READ = new CommandPacket();
    private static final CommandPacket _SERIALIZABLE = new CommandPacket();
    private static final CommandPacket _AUTOCOMMIT_ON = new CommandPacket();
    private static final CommandPacket _AUTOCOMMIT_OFF = new CommandPacket();
    private static final CommandPacket _COMMIT = new CommandPacket();
    private static final CommandPacket _ROLLBACK = new CommandPacket();

    static {
        _READ_UNCOMMITTED.packetId = 0;
        _READ_UNCOMMITTED.command = MySQLPacket.COM_QUERY;
        _READ_UNCOMMITTED.arg = "SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED".getBytes();
        _READ_COMMITTED.packetId = 0;
        _READ_COMMITTED.command = MySQLPacket.COM_QUERY;
        _READ_COMMITTED.arg = "SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED".getBytes();
        _REPEATED_READ.packetId = 0;
        _REPEATED_READ.command = MySQLPacket.COM_QUERY;
        _REPEATED_READ.arg = "SET SESSION TRANSACTION ISOLATION LEVEL REPEATABLE READ".getBytes();
        _SERIALIZABLE.packetId = 0;
        _SERIALIZABLE.command = MySQLPacket.COM_QUERY;
        _SERIALIZABLE.arg = "SET SESSION TRANSACTION ISOLATION LEVEL SERIALIZABLE".getBytes();
        _AUTOCOMMIT_ON.packetId = 0;
        _AUTOCOMMIT_ON.command = MySQLPacket.COM_QUERY;
        _AUTOCOMMIT_ON.arg = "SET autocommit=1".getBytes();
        _AUTOCOMMIT_OFF.packetId = 0;
        _AUTOCOMMIT_OFF.command = MySQLPacket.COM_QUERY;
        _AUTOCOMMIT_OFF.arg = "SET autocommit=0".getBytes();
        _COMMIT.packetId = 0;
        _COMMIT.command = MySQLPacket.COM_QUERY;
        _COMMIT.arg = "commit".getBytes();
        _ROLLBACK.packetId = 0;
        _ROLLBACK.command = MySQLPacket.COM_QUERY;
        _ROLLBACK.arg = "rollback".getBytes();
    }

    private long threadId;
    private int charsetIndex;
    private String charset;
    private volatile boolean autocommit;
    private long clientFlags;
    private boolean isAuthenticated;
    protected int maxPacketSize;
    private String user;
    private String password;
    private String schema;
    private Object attachment;
    private long lastActiveTime;
    private AtomicBoolean isRunning;
    private AtomicBoolean isQuit;
    private volatile boolean executed;
    private ResponseHandler handler = null;
    private MySQLConnectionPool pool;
    private HandshakePacket shakePacket;
    //做同步用，用于配置 connection pool 与 response handler
    private CountDownLatch configLatch;

    public MySQLConnection() {
        this.clientFlags = CLIENT_FLAGS;
        this.isRunning = new AtomicBoolean(false);
        this.isQuit = new AtomicBoolean(false);
        this.configLatch = new CountDownLatch(1);
        this.shakePacket = null;
        this.maxPacketSize = 16 * 1024 * 1024;
        this.autocommit = true;
    }

    public void authenticate() {
        AuthPacket packet = new AuthPacket();
        packet.packetId = 1;
        packet.clientFlags = clientFlags;
        packet.maxPacketSize = maxPacketSize;
        packet.charsetIndex = charsetIndex;
        packet.user = user;
        try {
            packet.password = passwd(password, shakePacket);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
        packet.database = schema;
        packet.write(this.getChannelHandlerContext());
    }

    public void rollback() {
        _ROLLBACK.write(this.getChannelHandlerContext());
    }

    public void commit() {
        _COMMIT.write(this.getChannelHandlerContext());
    }

    @Override
    public boolean close() {
        isQuit.set(true);
        boolean closed = super.close();
        if (closed) {
            pool.deActive();
        }
        return closed;
    }

    public void execute(RouteResultsetNode rrn, ServerConnection sc, boolean autocommit)
        throws UnsupportedEncodingException {
        executed = true;
        for (String stmt : rrn.getStatement()) {
            CommandPacket packet = new CommandPacket();
            packet.packetId = 0;
            packet.command = MySQLPacket.COM_QUERY;
            packet.arg = stmt.getBytes(getCharset());
            this.lastActiveTime = System.currentTimeMillis();
            packet.write(this.getChannelHandlerContext());
        }
    }

    public boolean isRunning() {
        return isRunning.get();
    }

    public void setRunning(boolean running) {
        this.isRunning.set(running);
    }

    public boolean isClosedOrQuit() {
        return isQuit.get();
    }

    public void quit() {
        if (isQuit.compareAndSet(false, true) && !isClosed()) {
            if (isAuthenticated) {
                // QS_TODO check
                write(QuitPacket.QUIT);
//                write(processor.getBufferPool().allocate());
            } else {
                close();
            }
        }
    }



    public void setIsQuit(boolean isQuit) {
        this.isQuit.set(isQuit);
    }

    public ResponseHandler getHandler() {
        return handler;
    }

    public void setHandler(ResponseHandler handler) {
        this.handler = handler;
    }

    public void setPool(MySQLConnectionPool pool) {
        this.pool = pool;
    }

    public CountDownLatch getConfigLatch() {
        return configLatch;
    }

    public long getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(long lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public void release() {
        this.attachment = null;
//        this.setHandler(null);
        pool.releaseChannel(this);
    }

    public HandshakePacket getShakePacket() {
        return shakePacket;
    }

    public void setShakePacket(HandshakePacket shakePacket) {
        this.shakePacket = shakePacket;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public void setCharsetIndex(int charsetIndex) {
        this.charsetIndex = charsetIndex;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getCharset() {
        return charset;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getSchema() {
        return schema;
    }

    private static byte[] passwd(String pass, HandshakePacket hs) throws NoSuchAlgorithmException {
        if (pass == null || pass.length() == 0) {
            return null;
        }
        byte[] passwd = pass.getBytes();
        int sl1 = hs.seed.length;
        int sl2 = hs.restOfScrambleBuff.length;
        byte[] seed = new byte[sl1 + sl2];
        System.arraycopy(hs.seed, 0, seed, 0, sl1);
        System.arraycopy(hs.restOfScrambleBuff, 0, seed, sl1, sl2);
        return SecurityUtil.scramble411(passwd, seed);
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public boolean isAutocommit() {
        return autocommit;
    }

    public boolean isExecuted() {
        return executed;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }
}
