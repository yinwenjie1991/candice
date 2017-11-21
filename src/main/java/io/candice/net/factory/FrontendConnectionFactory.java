package io.candice.net.factory;

import io.candice.CandicePrivileges;
import io.candice.net.connection.FrontendConnection;
import io.netty.channel.ChannelHandlerContext;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-10
 */
public class FrontendConnectionFactory {

    private static final AcceptIdGenerator ID_GENERATOR = new AcceptIdGenerator();

    protected static String charset = "utf8";

    public static FrontendConnection make() {
        FrontendConnection frontendConnection = new FrontendConnection();
        frontendConnection.setId(ID_GENERATOR.getId());
//        serverConnection.setChannelHandlerContext(context);
        frontendConnection.setPrivileges(new CandicePrivileges());
        frontendConnection.setCharset(charset);
        return frontendConnection;
    }

    /**
     * 前端连接ID生成器
     * mysql threadid generator
     */
    private static class AcceptIdGenerator {

        private static final long MAX_VALUE = 0xffffffffL;

        private long acceptId = 0L;
        private final Object lock = new Object();

        private long getId() {
            synchronized (lock) {
                if (acceptId >= MAX_VALUE) {
                    acceptId = 0L;
                }
                return ++acceptId;
            }
        }
    }
}
