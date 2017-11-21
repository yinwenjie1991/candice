package io.candice.config;

import io.candice.config.model.CandiceNodeConfig;
import io.candice.config.model.ClusterConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-19
 */
public class CandiceCluster {
    private final Map<String, CandiceNode> nodes;
    private final Map<String, List<String>> groups;

    public CandiceCluster(ClusterConfig clusterConf) {
        this.nodes = new HashMap<String, CandiceNode>(clusterConf.getNodes().size());
        this.groups = clusterConf.getGroups();
        for (CandiceNodeConfig conf : clusterConf.getNodes().values()) {
            String name = conf.getName();
            CandiceNode node = new CandiceNode(conf);
            this.nodes.put(name, node);
        }
    }

    public Map<String, CandiceNode> getNodes() {
        return nodes;
    }

    public Map<String, List<String>> getGroups() {
        return groups;
    }
}
