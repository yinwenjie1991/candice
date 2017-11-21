package io.candice.net.handler.backend;

import io.candice.net.connection.MySQLConnection;
import io.candice.net.handler.enums.BackendHandlerNameEnum;
import io.candice.net.mysql.*;
import io.candice.util.CharsetUtil;
import io.candice.util.SecurityUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 文件描述: 后端连接mysql握手认证
 * 作者: yinwenjie
 * 日期: 2017-11-16
 */
public class BackendMySQLAuthenticatorHandler extends ChannelInboundHandlerAdapter{

    private MySQLConnection source;

    public BackendMySQLAuthenticatorHandler(MySQLConnection source) {
        this.source = source;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] data = (byte[]) msg;
        source.setChannelHandlerContext(ctx);
        //等待MySQLConnection配置完成
        source.getConfigLatch().await();
        try {
            HandshakePacket packet = this.source.getShakePacket();
            if (packet == null) {
                packet = new HandshakePacket();
                packet.read(data);
                source.setShakePacket(packet);
                source.setThreadId(packet.threadId);

                // 设置字符集编码
                int charsetIndex = (packet.serverCharsetIndex & 0xff);
                String charset = CharsetUtil.getCharset(charsetIndex);
                if (charset != null) {
                    source.setCharsetIndex(charsetIndex);
                    source.setCharset(charset);
                } else {
                    throw new RuntimeException("Unknown charsetIndex:" + charsetIndex);
                }

                source.authenticate();
            } else {
                switch (data[4]) {
                    case OkPacket.FIELD_COUNT:
//                        source.setHandler(new MySQLConnectionHandler(source));
                        source.setAuthenticated(true);
                        ctx.pipeline().replace(BackendHandlerNameEnum.MYSQL_AUTH.getCode(), BackendHandlerNameEnum.MYSQL_CONN.getCode(),
                                new BackendMySQLConnectionHandler(source));
                        source.getHandler().connectionAcquired(source);
//                        if (listener != null) {
//                            listener.connectionAcquired(source);
//                        }
                        break;
                    case ErrorPacket.FIELD_COUNT:
                        ErrorPacket err = new ErrorPacket();
                        err.read(data);
                        throw new RuntimeException(new String(err.message));
                    case EOFPacket.FIELD_COUNT:
                        auth323(data[3]);
                        break;
                    default:
                        throw new RuntimeException("Unknown Packet!");
                }
            }
        } catch (Exception e) {
            // error, 调用handler
            this.source.getHandler().connectionError(e, source);
            throw e;
        }

        //调用下一个 handler的 channelRead
        super.channelRead(ctx, msg);
    }

    private void auth323(byte packetId) {
        // 发送323响应认证数据包
        Reply323Packet r323 = new Reply323Packet();
        r323.packetId = ++packetId;
        String pass = source.getPassword();
        if (pass != null && pass.length() > 0) {
            byte[] seed = source.getShakePacket().seed;
            r323.seed = SecurityUtil.scramble323(pass, new String(seed)).getBytes();
        }
        r323.write(source.getChannelHandlerContext());
    }

}
