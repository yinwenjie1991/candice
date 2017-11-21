package io.candice.net.handler.enums;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-12
 */
public enum FrontHandlerNameEnum {

    IDLE("frontIdleHandler"),

    GROUP("frontGroupHandler"),

    MYSQL_DECODER("mysqlDecoder"),

    AUTH("frontAuthHandler"),

    COMMAND("frontendCommandHandler")
    ;

    private String handlerName;

    private FrontHandlerNameEnum(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getCode() {
        return this.handlerName;
    }
}
