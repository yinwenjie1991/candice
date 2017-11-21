package io.candice.net.handler;

import java.util.Set;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-14
 */
public interface FrontendPrivileges {

    /**
     * 检查schema是否存在
     */
    boolean schemaExists(String schema);

    /**
     * 检查用户是否存在，并且可以使用host实行隔离策略。
     */
    boolean userExists(String user, String host);

    /**
     * 提供用户的服务器端密码
     */
    String getPassword(String user);

    /**
     * 提供有效的用户schema集合
     */
    Set<String> getUserSchemas(String user);

}
