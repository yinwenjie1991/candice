package io.candice.config.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-19
 */
public class QuarantineConfig {

    private final Map<String, Set<String>> hosts;

    public QuarantineConfig() {
        hosts = new HashMap<String, Set<String>>();
    }

    public Map<String, Set<String>> getHosts() {
        return hosts;
    }
}
