package io.candice.config.loader.xml;

import io.candice.config.loader.ConfigLoader;
import io.candice.config.loader.SchemaLoader;
import io.candice.config.model.*;

import java.util.Map;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-19
 */
public class XMLConfigLoader implements ConfigLoader{

    private final Map<String, DataSourceConfig> dataSources;
    private final Map<String, DataNodeConfig>   dataNodes;
    private final Map<String, SchemaConfig>     schemas;
    private final SystemConfig                  system;
    private final Map<String, UserConfig>       users;
    private final QuarantineConfig              quarantine;
    private final ClusterConfig                 cluster;

    public XMLConfigLoader(SchemaLoader schemaLoader, String configFolder) {

        this.dataSources = schemaLoader.getDataSources();
        this.dataNodes = schemaLoader.getDataNodes();
        this.schemas = schemaLoader.getSchemas();
        schemaLoader = null;
        XMLServerLoader serverLoader = new XMLServerLoader(configFolder);
        this.system = serverLoader.getSystem();
        this.users = serverLoader.getUsers();
        this.quarantine = serverLoader.getQuarantine();
        this.cluster = serverLoader.getCluster();
    }

    public ClusterConfig getClusterConfig() {
        return cluster;
    }

    public QuarantineConfig getQuarantineConfig() {
        return quarantine;
    }

    public UserConfig getUserConfig(String user) {
        return users.get(user);
    }

    public Map<String, UserConfig> getUserConfigs() {
        return users;
    }

    public SystemConfig getSystemConfig() {
        return system;
    }

    //    @Override
    //    public Map<String, RuleAlgorithm> getRuleFunction() {
    //        return functions;

    //    @Override
    //    public Set<RuleConfig> listRuleConfig() {
    //        return rules;
    //    }

    public Map<String, SchemaConfig> getSchemaConfigs() {
        return schemas;
    }

    public Map<String, DataNodeConfig> getDataNodes() {
        return dataNodes;
    }

    public Map<String, DataSourceConfig> getDataSources() {
        return dataSources;
    }

    public SchemaConfig getSchemaConfig(String schema) {
        return schemas.get(schema);
    }

}
