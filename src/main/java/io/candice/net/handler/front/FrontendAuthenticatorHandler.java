package io.candice.net.handler.front;

import io.candice.config.Capabilities;
import io.candice.config.ErrorCode;
import io.candice.config.Versions;
import io.candice.net.NettyAcceptor;
import io.candice.net.connection.FrontendConnection;
import io.candice.net.handler.FrontendPrivileges;
import io.candice.net.handler.enums.FrontHandlerNameEnum;
import io.candice.net.mysql.AuthPacket;
import io.candice.net.mysql.HandshakePacket;
import io.candice.net.mysql.MySQLPacket;
import io.candice.net.mysql.QuitPacket;
import io.candice.util.RandomUtil;
import io.candice.util.SecurityUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-11
 */
public class FrontendAuthenticatorHandler extends ChannelInboundHandlerAdapter{

    private static final Logger LOGGER = Logger.getLogger(FrontendAuthenticatorHandler.class);
    private static final byte[] AUTH_OK = new byte[] { 7, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0 };

    private FrontendConnection frontendConnection;

    public FrontendAuthenticatorHandler(FrontendConnection frontendConnection) {
        this.frontendConnection = frontendConnection;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        // 生成认证数据
        byte[] rand1 = RandomUtil.randomBytes(8);
        byte[] rand2 = RandomUtil.randomBytes(12);

        // 保存认证数据
        byte[] seed = new byte[rand1.length + rand2.length];
        System.arraycopy(rand1, 0, seed, 0, rand1.length);
        System.arraycopy(rand2, 0, seed, rand1.length, rand2.length);
        this.frontendConnection.setSeed(seed);
        HandshakePacket hs = new HandshakePacket();
        hs.packetId = 0;
        hs.protocolVersion = Versions.PROTOCOL_VERSION;
        hs.serverVersion = Versions.SERVER_VERSION;
        hs.threadId = frontendConnection.getId();
        hs.seed = rand1;
        hs.serverCapabilities = getServerCapabilities();
        hs.serverCharsetIndex = (byte) (frontendConnection.getCharsetIndex() & 0xff);
        hs.serverStatus = 2;    //autocommit
        hs.restOfScrambleBuff = rand2;
        hs.write(ctx);
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] bytes = (byte[]) msg;
        if (bytes.length == QuitPacket.QUIT.length && bytes[4] == MySQLPacket.COM_QUIT) {
            ctx.close();
            return;
        }
        AuthPacket authPacket = new AuthPacket();
        authPacket.read(bytes);

//        super.channelRead(ctx, msg);
        //设置host port
        frontendConnection.setHost(((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress());
        frontendConnection.setPort(((InetSocketAddress) ctx.channel().remoteAddress()).getPort());
        frontendConnection.setLocalPort(((InetSocketAddress)ctx.channel().localAddress()).getPort());
        // check user
        if (!checkUser(authPacket.user, frontendConnection.getHost())) {
            failure(ErrorCode.ER_ACCESS_DENIED_ERROR, "Access denied for user '" + authPacket.user + "'");
            return;
        }

        // check password
        if (!checkPassword(authPacket.password, authPacket.user)) {
            failure(ErrorCode.ER_ACCESS_DENIED_ERROR, "Access denied for user '" + authPacket.user + "'");
            return;
        }

        // check schema
        switch (checkSchema(authPacket.database, authPacket.user)) {
            case ErrorCode.ER_BAD_DB_ERROR:
                failure(ErrorCode.ER_BAD_DB_ERROR, "Unknown database '" + authPacket.database + "'");
                break;
            case ErrorCode.ER_DBACCESS_DENIED_ERROR:
                String s = "Access denied for user '" + authPacket.user + "' to database '" + authPacket.database + "'";
                failure(ErrorCode.ER_DBACCESS_DENIED_ERROR, s);
                break;
            default:
                success(authPacket, ctx);
        }

        // 这里不能再调用, 否则会使得包的顺序错乱
        // candice会多发一个包，客户端则会慢一个包，即当前收到的包是上一个指令的包
//        super.channelRead(ctx, msg);
    }



    protected int getServerCapabilities() {
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
        // flag |= ServerDefs.CLIENT_RESERVED;
        flag |= Capabilities.CLIENT_SECURE_CONNECTION;
        return flag;
    }

    protected boolean checkUser(String user, String host) {
        return frontendConnection.getPrivileges().userExists(user, host);
    }

    protected void failure(int errno, String info) {
        LOGGER.error(frontendConnection.toString() + info);
        frontendConnection.writeErrMessage((byte) 2, errno, info);
    }

    protected boolean checkPassword(byte[] password, String user) {
        String pass = frontendConnection.getPrivileges().getPassword(user);

        // check null
        if (pass == null || pass.length() == 0) {
            if (password == null || password.length == 0) {
                return true;
            } else {
                return false;
            }
        }
        if (password == null || password.length == 0) {
            return false;
        }

        // encrypt
        byte[] encryptPass = null;
        try {
            encryptPass = SecurityUtil.scramble411(pass.getBytes(), frontendConnection.getSeed());
        } catch (NoSuchAlgorithmException e) {
            LOGGER.warn(frontendConnection.toString(), e);
            return false;
        }
        if (encryptPass != null && (encryptPass.length == password.length)) {
            int i = encryptPass.length;
            while (i-- != 0) {
                if (encryptPass[i] != password[i]) {
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }

    protected int checkSchema(String schema, String user) {
        if (schema == null) {
            return 0;
        }
        FrontendPrivileges privileges = frontendConnection.getPrivileges();
        if (!privileges.schemaExists(schema)) {
            return ErrorCode.ER_BAD_DB_ERROR;
        }
        Set<String> schemas = privileges.getUserSchemas(user);
        if (schemas == null || schemas.size() == 0 || schemas.contains(schema)) {
            return 0;
        } else {
            return ErrorCode.ER_DBACCESS_DENIED_ERROR;
        }
    }

    protected void success(AuthPacket auth, ChannelHandlerContext ctx) {
        frontendConnection.setAuthenticated(true);
        frontendConnection.setUser(auth.user);
        frontendConnection.setSchema(auth.database);
        frontendConnection.setCharsetIndex(auth.charsetIndex);
        ctx.pipeline().replace( FrontHandlerNameEnum.AUTH.getCode()
                , FrontHandlerNameEnum.COMMAND.getCode()
                , new FrontendCommandHandler(frontendConnection));
        if (LOGGER.isInfoEnabled()) {
            StringBuilder s = new StringBuilder();
            s.append(frontendConnection).append('\'').append(auth.user).append("' login success");
            byte[] extra = auth.extra;
            if (extra != null && extra.length > 0) {
                s.append(",extra:").append(new String(extra));
            }
            LOGGER.info(s.toString());
        }
        ctx.writeAndFlush(ctx.alloc().buffer().writeBytes(AUTH_OK));
    }
}
