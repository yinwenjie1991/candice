package io.candice;

import io.candice.config.CandiceConfig;
import io.candice.net.NettyAcceptor;
import io.candice.net.NettyConnector;
import io.candice.server.mysql.MySQLDataNode;
import io.candice.server.mysql.nio.MySQLConnectionPool;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-14
 */
public class CandiceServer {

    public static final String NAME = "Candice";
    private static final long LOG_WATCH_DELAY = 60000L;
    private static final long TIME_UPDATE_PERIOD = 20L;
    private static final CandiceServer INSTANCE = new CandiceServer();
    private static final Logger LOGGER = Logger.getLogger(CandiceServer.class);

    private final CandiceConfig config;
    private int eventLoopGroupNum;
    private final AtomicBoolean isOnline;
    private NettyConnector nettyConnector;
    private NettyAcceptor nettyServer;

    public static final CandiceServer getInstance() {
        return INSTANCE;
    }

    private CandiceServer() {
        //读取配置文件
        config = new CandiceConfig();
        this.isOnline = new AtomicBoolean(true);

    }

    public void beforeStart(String dateFormat) {
        String home = System.getProperty("candice.home");
        if (home == null) {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            LogLog.warn(sdf.format(new Date()) + " [candice.home] is not set.");
        } else {
            Log4jInitializer.configureAndWatch(home + "/conf/log4j.xml", LOG_WATCH_DELAY);
        }
    }

    public void startup() {
        LOGGER.info("===============================================");
        LOGGER.info(NAME + " is ready to startup ...");

        nettyConnector = new NettyConnector(NAME + "Connector", config);
        nettyConnector.config();

        //将nettyConnector注入到每个mysql连接池中
        configConnector(nettyConnector);
        //init mysql connections
        for (MySQLDataNode node : config.getDataNodes().values()) {
            //目前初始连接暂时定为1，后面根据需求配置修改
            node.init(5, 0);
        }

        nettyServer = new NettyAcceptor(NAME + "Server", config.getSystem().getServerPort() ,config);
        nettyServer.start();

    }

    public CandiceConfig getConfig() {
        return config;
    }

    public boolean isOnline() {
        return isOnline.get();
    }

    public void offline() {
        isOnline.set(false);
    }

    public void online() {
        isOnline.set(true);
    }

    public int getEventLoopGroupNum() {
        return eventLoopGroupNum;
    }

    public NettyConnector getNettyConnector() {
        return nettyConnector;
    }

    public NettyAcceptor getNettyServer() {
        return nettyServer;
    }

    public static void main(String[] args) {
        CandiceServer candiceServer = new CandiceServer();
        System.out.println("stop !!! ---");

    }

    private void configConnector(NettyConnector nettyConnector) {

        for (MySQLDataNode node : config.getDataNodes().values()) {
            for (MySQLConnectionPool pool : node.getSources()) {
                pool.setNettyConnector(nettyConnector);
            }
        }
    }
}
