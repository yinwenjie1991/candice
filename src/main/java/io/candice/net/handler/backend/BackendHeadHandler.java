package io.candice.net.handler.backend;

import io.candice.net.connection.MySQLConnection;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-16
 */
public class BackendHeadHandler extends ChannelInboundHandlerAdapter{

    private MySQLConnection source;

    public BackendHeadHandler(MySQLConnection source) {
        this.source = source;
    }

    public MySQLConnection getSource() {
        return source;
    }

    public void setSource(MySQLConnection source) {
        this.source = source;
    }
}
