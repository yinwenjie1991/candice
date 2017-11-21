package io.candice.config.util;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-16
 */
public class ConfigException extends RuntimeException{

    private static final long serialVersionUID = 1432806171255151339L;

    public ConfigException() {
        super();
    }

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(Throwable cause) {
        super(cause);
    }
}
