package io.candice.config.util;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-18
 */
public interface Visitor {

    void visit(String name, Class<?> type, Class<?> definedIn, Object value);

}
