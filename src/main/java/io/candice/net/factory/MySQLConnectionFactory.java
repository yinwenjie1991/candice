package io.candice.net.factory;

import io.candice.net.connection.MySQLConnection;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-13
 */
public class MySQLConnectionFactory {

    public MySQLConnection make() {
        MySQLConnection c = new MySQLConnection();
        return c;
    }
}
