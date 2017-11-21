package io.candice.config;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-12
 */
public interface Versions {

    /** 协议版本 */
    byte PROTOCOL_VERSION = 10;

    /** 服务器版本 */
    byte[] SERVER_VERSION = "5.1.1-candice".getBytes();
}
