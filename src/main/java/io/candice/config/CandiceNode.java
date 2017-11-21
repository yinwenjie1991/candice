package io.candice.config;

import io.candice.config.model.CandiceNodeConfig;
import org.apache.log4j.Logger;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-19
 */
public class CandiceNode {
    private static final Logger LOGGER = Logger.getLogger(CandiceNode.class);

    private final String name;
    private final CandiceNodeConfig config;

    public CandiceNode(CandiceNodeConfig config) {
        this.name = config.getName();
        this.config = config;
    }

    public String getName() {
        return name;
    }

    public CandiceNodeConfig getConfig() {
        return config;
    }

    public boolean isOnline() {
        return (true);
    }
}
