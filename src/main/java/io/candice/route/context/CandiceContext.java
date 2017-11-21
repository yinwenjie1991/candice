package io.candice.route.context;

import io.candice.CandiceServer;
import io.candice.server.mysql.MySQLDataNode;

import java.util.Map;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-03
 */
public class CandiceContext {

    public static Map<String, MySQLDataNode> getMysqlDataNode() {
        return CandiceServer.getInstance().getConfig().getDataNodes();
    }

}
