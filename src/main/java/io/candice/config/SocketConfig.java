package io.candice.config;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-04
 */
public class SocketConfig {

    public static final int CONNECT_TIMEOUT_MILLIS = 6000;

    public static final int SO_TIMEOUT =  45*1000;

    public static int BACKEND_SO_RCVBUF = 4 * 1024 * 1024;

    public static int BACKEND_SO_SNDBUF = 1024 * 1024;// mysql 5.6

}
