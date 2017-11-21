package io.candice.server.mysql.nio;

import io.candice.config.Alarms;
import io.candice.config.model.DataSourceConfig;
import io.candice.net.NettyConnector;
import io.candice.net.connection.MySQLConnection;
import io.candice.net.handler.backend.BackendHeadHandler;
import io.candice.net.handler.enums.BackendHandlerNameEnum;
import io.candice.server.mysql.MySQLDataNode;
import io.candice.server.mysql.common.DataSource;
import io.candice.server.mysql.handler.DelegateResponseHandler;
import io.candice.server.mysql.handler.ResponseHandler;
import io.netty.channel.ChannelFuture;
import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-22
 */
public class MySQLConnectionPool implements DataSource{

    private static final Logger LOGGER = Logger.getLogger(MySQLConnectionPool.class);
    private static final Logger alarm = Logger.getLogger("alarm");

    private final MySQLDataNode dataNode;
    private final int index;
    private final String name;
    private final ReentrantLock lock = new ReentrantLock();
    private final DataSourceConfig config;
    private final int size;
    private NettyConnector nettyConnector;

//    private final MySQLConnectionFactory factory;
    private final MySQLConnection[] items;

    private AtomicInteger activeCount = new AtomicInteger(0);

    private AtomicInteger idleCount = new AtomicInteger(0);

    public MySQLConnectionPool(MySQLDataNode node, int index, DataSourceConfig config, int size) {
        this.dataNode = node;
        this.size = size;
        this.config = config;
        this.name = config.getName();
        this.index = index;
        this.items = new MySQLConnection[size];
    }

    public MySQLConnection getConnection(final ResponseHandler handler, final Object attachment) throws Exception {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            //连接数过多
            if (activeCount.get() + idleCount.get() >= size) {
                StringBuilder s = new StringBuilder();
                s.append(Alarms.DEFAULT).append("[name=").append(name).append(",active=");
                s.append(activeCount).append(",size=").append(size).append(']');
                alarm.error(s.toString());
            }

            //获取连接池
            final MySQLConnection[] items = this.items;
            for (int i = 0, len = items.length; idleCount.get() > 0 && i < len; ++i) {
                if (items[i] != null && !items[i].isRunning()) {
                    MySQLConnection conn = items[i];
                    items[i] = null;
                    idleCount.getAndDecrement();
                    if (conn.isClosedOrQuit()) {
                        continue;
                    } else {
                        activeCount.incrementAndGet();
                        conn.setAttachment(attachment);
                        conn.setHandler(handler);
                        handler.connectionAcquired(conn);
                        return conn;
                    }
                }
            }
            activeCount.incrementAndGet();
        } finally {
            lock.unlock();
        }

        //创建新的连接
        ChannelFuture f = nettyConnector.getB().connect(this.getConfig().getHost(), this.getConfig().getPort())
                .sync();
        if (f.isSuccess()) {
            BackendHeadHandler backendHeadHandler = (BackendHeadHandler) f.channel().pipeline().get(BackendHandlerNameEnum.HEAD.getCode());
            MySQLConnection mySQLConnection = backendHeadHandler.getSource();
            //配置相关参数
            mySQLConnection.setHandler(new DelegateResponseHandler(handler) {

                private boolean deactived;

                @Override
                public void connectionError(Throwable e, MySQLConnection conn) {
                    lock.lock();
                    try {
                        if (!deactived) {
                            activeCount.decrementAndGet();
                            deactived = true;
                        }
                    } finally {
                        lock.unlock();
                    }
                    handler.connectionError(e, conn);
                }

                @Override
                public void connectionAcquired(MySQLConnection conn) {
                    conn.setAttachment(attachment);
                    super.connectionAcquired(conn);
                }
            });
            mySQLConnection.setPool(this);
            mySQLConnection.setHandler(handler);
            mySQLConnection.setHost(this.getConfig().getHost());
            mySQLConnection.setPort(this.getConfig().getPort());
            mySQLConnection.setUser(this.getConfig().getUser());
            mySQLConnection.setPassword(this.getConfig().getPassword());
            mySQLConnection.setSchema(this.getConfig().getDatabase());
            mySQLConnection.getConfigLatch().countDown();
            return mySQLConnection;
        } else {
            activeCount.decrementAndGet();
            return null;
        }
    }

    /**
     * 连接池连接回收
     * 回收但是不关闭
     * @param c MySQLConnection
     */
    public void releaseChannel(MySQLConnection c) {
        if ( c == null || c.isClosedOrQuit()) {
            return ;
        }
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final MySQLConnection[] items = this.items;
            for (int i = 0; i < items.length; i++) {
                if (items[i] == null) {
                    idleCount.incrementAndGet();
                    activeCount.decrementAndGet();
                    c.setLastActiveTime(System.currentTimeMillis());
                    items[i] = c;
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(getName() + "[" + getIndex() + "] activeCount[" + activeCount + "]idleCount["
                                + idleCount + "]release connection-->" + i);
                    }
                    return;
                }
            }
        } finally {
            lock.unlock();
        }

    }

    @Override
    public MySQLDataNode getNode() {
        return dataNode;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DataSourceConfig getConfig() {
        return config;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int getActiveCount() {
        return activeCount.get();
    }

    @Override
    public int getIdleCount() {
        return idleCount.get();
    }

    @Override
    public void startHeartbeat() {

    }

    @Override
    public void stopHeartbeat() {

    }

    @Override
    public void doHeartbeat() {

    }

    @Override
    public void deActive() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            activeCount.decrementAndGet();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clear() {

    }

    @Override
    public void idleCheck(long timeout) {

    }

    public NettyConnector getNettyConnector() {
        return nettyConnector;
    }

    public void setNettyConnector(NettyConnector nettyConnector) {
        this.nettyConnector = nettyConnector;
    }
}
