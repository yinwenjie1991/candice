package io.candice.server.mysql;

import io.candice.config.model.DataSourceConfig;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-19
 */
public class MySQLDataSource {

    private final MySQLDataNode node;
    private final int index;
    private final String name;
    private final DataSourceConfig config;
    private final int size;

    public MySQLDataSource(MySQLDataNode node, int index, DataSourceConfig config, int size) {
        this.node = node;
        this.index = index;
        this.name = config.getName();
        this.config = config;
        this.size = size;
    }

    public MySQLDataNode getNode() {
        return node;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public DataSourceConfig getConfig() {
        return config;
    }

    public int getSize() {
        return size;
    }
}
