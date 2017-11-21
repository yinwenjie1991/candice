package io.candice.config.loader;

import io.candice.config.model.*;

import java.util.Map;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-19
 */
public interface ConfigLoader {
    SchemaConfig getSchemaConfig(String schema);

    Map<String, SchemaConfig> getSchemaConfigs();

    Map<String, DataNodeConfig> getDataNodes();

    Map<String, DataSourceConfig> getDataSources();

    SystemConfig getSystemConfig();

    UserConfig getUserConfig(String user);

    Map<String, UserConfig> getUserConfigs();

    QuarantineConfig getQuarantineConfig();

    ClusterConfig getClusterConfig();
}
