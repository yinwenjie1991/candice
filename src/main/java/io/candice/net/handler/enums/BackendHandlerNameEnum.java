package io.candice.net.handler.enums;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-16
 */
public enum BackendHandlerNameEnum {

    HEAD("headHandler"),

    MYSQL_DECODER("mysqlDecoder"),

    MYSQL_AUTH("mySQLAuthenticatorHandler"),

    MYSQL_CONN("mySQLConnectionHandler"),

    TAIL("tailHeader");

    private String handlerName;

    private BackendHandlerNameEnum(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getCode() {
        return this.handlerName;
    }
}
