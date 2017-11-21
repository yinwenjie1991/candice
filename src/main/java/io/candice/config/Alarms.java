package io.candice.config;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-14
 */
public interface Alarms {
    /** 默认报警关键词 **/
    String DEFAULT = "#!Candice#";

    /** 集群无有效的节点可提供服务 **/
    String CLUSTER_EMPTY = "#!CLUSTER_EMPTY#";

    /** 数据节点的数据源发生切换 **/
    String DATANODE_SWITCH = "#!DN_SWITCH#";

    /** 隔离区非法用户访问 **/
    String QUARANTINE_ATTACK = "#!QT_ATTACK#";
}
