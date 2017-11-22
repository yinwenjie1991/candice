package io.candice.net.handler.front;

import io.candice.config.ErrorCode;
import io.candice.net.connection.FrontendConnection;
import io.candice.net.mysql.MySQLPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-16
 */
public class FrontendCommandHandler extends ChannelInboundHandlerAdapter {

    private FrontendConnection frontendConnection;

    public FrontendCommandHandler(FrontendConnection frontendConnection) {
        this.frontendConnection = frontendConnection;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("FrontendCommandHandler execute");
        byte[] data = (byte[]) msg;

        switch (data[4]) {
            case MySQLPacket.COM_INIT_DB:
                frontendConnection.initDB(data);
                break;
            case MySQLPacket.COM_QUERY:
                //query操作
                frontendConnection.query(data);
                break;
            case MySQLPacket.COM_PING:
                frontendConnection.ping();
                break;
            case MySQLPacket.COM_QUIT:
                frontendConnection.close();
                break;
            case MySQLPacket.COM_PROCESS_KILL:
                frontendConnection.kill(data);
                break;
            case MySQLPacket.COM_STMT_PREPARE:
                // todo 目前candice不支持sql_prepare
                frontendConnection.stmtPrepare(data);
                break;
            case MySQLPacket.COM_STMT_EXECUTE:
                frontendConnection.stmtExecute(data);
                break;
            case MySQLPacket.COM_STMT_CLOSE:
                //未完成
                frontendConnection.stmtClose(data);
                break;
            case MySQLPacket.COM_HEARTBEAT:
                frontendConnection.heartbeat(data);
                break;
            default:
                frontendConnection.writeErrMessage(ErrorCode.ER_UNKNOWN_COM_ERROR, "Unkown command");
        }

        super.channelRead(ctx, msg);
    }
}
