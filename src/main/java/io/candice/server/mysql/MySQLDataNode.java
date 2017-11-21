package io.candice.server.mysql;


import io.candice.config.Alarms;
import io.candice.config.model.DataNodeConfig;
import io.candice.net.connection.MySQLConnection;
import io.candice.server.mysql.common.DataSource;
import io.candice.server.mysql.handler.GetConnectionHandler;
import io.candice.server.mysql.handler.ResponseHandler;
import io.candice.server.mysql.nio.MySQLConnectionPool;
import org.apache.log4j.Logger;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-19
 */
public class MySQLDataNode {

    private static final Logger LOGGER = Logger.getLogger(MySQLDataNode.class);
    private static final Logger ALARM = Logger.getLogger("alarm");

    private final String name;
    private final DataNodeConfig config;
//    private MySQLDataSource[] sources;
    private MySQLConnectionPool[] dataSources;
    private int activedIndex;
    private volatile boolean initSuccess = false;

    public MySQLDataNode(DataNodeConfig dataNodeConfig) {
        this.name = dataNodeConfig.getName();
        this.config = dataNodeConfig;
        this.activedIndex = 0;
    }

    public void init(int size, int index) {
        if (initSuccess) {
            return;
        }
        if (!checkIndex(index)) {
            index = 0;
        }
        int active = -1;
        for (int i = 0; i < getSources().length; i++) {
            int j = loop(i + index);
            if (initNIOSource( dataSources[j], size)) {
                active = j;
                break;
            }
        }
        if (checkIndex(active)) {
            activedIndex = active;
            initSuccess = true;
            LOGGER.info(getMessage(active, " init success"));
        } else {
            initSuccess = false;
            StringBuilder s = new StringBuilder();
            s.append(Alarms.DEFAULT).append(name).append(" init failure");
            ALARM.error(s.toString());
        }
    }

    private int loop(int i) {
        return i < getSources().length ? i : (i - getSources().length);
    }

    private boolean checkIndex(int i) {
        return i >= 0 && i < getSources().length;
    }

    public String getName() {
        return name;
    }

    public DataNodeConfig getConfig() {
        return config;
    }

    public void setDataSources(MySQLConnectionPool[] dataSources) {
        this.dataSources = dataSources;
    }

    public MySQLConnectionPool[] getSources() {
            return dataSources;
    }

    public DataSource getSource() {
        return dataSources[activedIndex];
    }

    public void getConnection(ResponseHandler handler, Object attachment) throws Exception {
        getConnection(handler, attachment, activedIndex);
    }

    public void getConnection(ResponseHandler handler, Object attachment, int i) throws Exception {
        if (initSuccess) {
            MySQLConnectionPool pool = dataSources[i];
            pool.getConnection(handler, attachment);
        } else {
            throw new IllegalArgumentException("Invalid DataSource:"
                    + activedIndex + ", init fail!");
        }
    }

    private boolean initNIOSource(MySQLConnectionPool ds, int size) {
        int connSize = size < ds.size() ? size : ds.size();
        MySQLConnection[] list = new MySQLConnection[connSize];

        CopyOnWriteArrayList<MySQLConnection> mySQLConnections = new CopyOnWriteArrayList<MySQLConnection>();
        GetConnectionHandler getConnectionHandler = new GetConnectionHandler(mySQLConnections, connSize);
        for (int i = 0; i< list.length ; i++) {
            try {
                list[i] = ds.getConnection( getConnectionHandler, null);
            } catch (Exception e) {
                LOGGER.warn(getMessage(ds.getIndex(), "init error"), e);
            }
        }
        long timeOut = System.currentTimeMillis() + 20 * 1000;

        while ( !getConnectionHandler.finished() && (System.currentTimeMillis() < timeOut)) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                LOGGER.error("connection pool initError", e);
            }
        }
        LOGGER.info("init result :" + getConnectionHandler.getStatusInfo());
        return !mySQLConnections.isEmpty();
    }

    private String getMessage(int index, String info) {
        return new StringBuilder().append(name).append(':').append(index).append(info).toString();
    }

    public int getActivedIndex() {
        return activedIndex;
    }
}
