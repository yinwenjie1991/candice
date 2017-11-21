package io.candice.config.util;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-18
 */
public class ObjectAccessException extends RuntimeException{

    private static final long serialVersionUID = 1151249659203914336L;

    public ObjectAccessException(String message) {
        super(message);
    }

    public ObjectAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
