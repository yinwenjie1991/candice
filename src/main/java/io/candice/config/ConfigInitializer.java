package io.candice.config;

import io.candice.CandiceStartup;
import io.candice.config.loader.ConfigLoader;
import io.candice.config.loader.SchemaLoader;
import io.candice.config.loader.xml.XMLConfigLoader;
import io.candice.config.loader.xml.XMLSchemaLoader;
import io.candice.config.model.*;
import io.candice.config.util.ConfigException;
import io.candice.server.mysql.MySQLDataNode;
import io.candice.server.mysql.MySQLDataSource;
import io.candice.server.mysql.nio.MySQLConnectionPool;
import io.candice.util.SplitUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 文件描述: candice配置启动类
 * 作者: yinwenjie
 * 日期: 2017-09-15
 */
public class ConfigInitializer {

    private volatile SystemConfig system;
//    private volatile CandiceCluster cluster;
    private volatile QuarantineConfig quarantine;
    private volatile Map<String, UserConfig> users;
    private volatile Map<String, SchemaConfig> schemas;
    private volatile Map<String, MySQLDataNode> dataNodes;
    private volatile Map<String, DataSourceConfig> dataSources;

    public ConfigInitializer() {
        boolean selfCp = CandiceStartup.hasSelfConfigPath();

        //读取schema文件以及rule文件
        SchemaLoader schemaLoader = null;
        if (selfCp) {
            schemaLoader = new XMLSchemaLoader(CandiceStartup.getConfigPath());
        } else {
            schemaLoader = new XMLSchemaLoader();
        }
        //读取server.xml
        XMLConfigLoader configLoader = new XMLConfigLoader(schemaLoader, CandiceStartup.getConfigPath());

        this.system = configLoader.getSystemConfig();
        this.users = configLoader.getUserConfigs();
        this.schemas = configLoader.getSchemaConfigs();
        this.dataSources = configLoader.getDataSources();
        this.dataNodes = initDataNodes(configLoader);
        this.quarantine = configLoader.getQuarantineConfig();

        checkConfig();
    }

    private Map<String, MySQLDataNode> initDataNodes(ConfigLoader configLoader) {
        Map<String, DataNodeConfig> nodeConfs = configLoader.getDataNodes();
        Map<String, MySQLDataNode> nodes = new HashMap<String, MySQLDataNode>(nodeConfs.size());
        for (DataNodeConfig conf : nodeConfs.values()) {
            MySQLDataNode dataNode = getDataNode(conf);
            if (nodes.containsKey(dataNode.getName())) {
                throw new ConfigException("dataNode " + dataNode.getName() + " duplicated!");
            }
            nodes.put(dataNode.getName(), dataNode);
        }
        return nodes;
    }

    private MySQLDataNode getDataNode(DataNodeConfig dnc) {
        String[] dsNames = SplitUtil.split(dnc.getDataSource(), ',');
        checkDataSourceExists(dsNames);
        MySQLDataNode node = new MySQLDataNode(dnc);
        int size = dnc.getPoolSize();
//        MySQLDataSource[] dsList = new MySQLDataSource[dsNames.length];
        MySQLConnectionPool[] poolList=new MySQLConnectionPool[dsNames.length];
        for (int i = 0; i < dsNames.length; i++) {
            DataSourceConfig dsc = dataSources.get(dsNames[i]);
            poolList[i] = new MySQLConnectionPool(node, i, dsc, size);
            node.setDataSources(poolList);
        }
//        node.setDataSources(dsList);

        return node;
    }

    private void checkDataSourceExists(String...nodes) {
        if (nodes == null || nodes.length < 1) {
            return;
        }
        for (String node : nodes) {
            if (!dataSources.containsKey(node)) {
                throw new ConfigException("dataSource '" + node + "' is not found!");
            }
        }
    }

    public SystemConfig getSystem() {
        return system;
    }

    private void checkConfig() throws ConfigException {
        if (users == null || users.isEmpty())
            return;
        for (UserConfig uc : users.values()) {
            if (uc == null) {
                continue;
            }
            Set<String> authSchemas = uc.getSchemas();
            if (authSchemas == null) {
                continue;
            }
            for (String schema : authSchemas) {
                if (!schemas.containsKey(schema)) {
                    String errMsg = "schema " + schema + " refered by user " + uc.getName() + " is not exist!";
                    throw new ConfigException(errMsg);
                }
            }
        }
    }

    public Map<String, UserConfig> getUsers() {
        return users;
    }

    public Map<String, SchemaConfig> getSchemas() {
        return schemas;
    }

    public Map<String, MySQLDataNode> getDataNodes() {
        return dataNodes;
    }

    public Map<String, DataSourceConfig> getDataSources() {
        return dataSources;
    }

    public QuarantineConfig getQuarantine() {
        return quarantine;
    }
}
