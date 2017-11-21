package io.candice.server.mysql.common;

import io.candice.config.model.DataSourceConfig;
import io.candice.server.mysql.MySQLDataNode;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-19
 */
public interface DataSource {

    public MySQLDataNode getNode();

    public int getIndex();

    public String getName();

    public DataSourceConfig getConfig();

    public int size() ;

    public int getActiveCount();

    public int getIdleCount();

    public MySQLHeartbeat getHeartbeat();

    public SQLRecorder getSqlRecorder() ;

    public void startHeartbeat();

    public void stopHeartbeat();

    public void doHeartbeat();

    public void deActive();

    public void clear();

    public void idleCheck(long timeout);


}
