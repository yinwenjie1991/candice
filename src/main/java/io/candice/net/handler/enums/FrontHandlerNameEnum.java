package io.candice.net.handler.

enum;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-12
 */
public enum FrontHandlerNameEnum {

    GROUP("frontGroupHandler"),
    
    ;

    private String handlerName;

    private FrontHandlerNameEnum(String handlerName) {
        this.handlerName = handlerName;
    }
}
