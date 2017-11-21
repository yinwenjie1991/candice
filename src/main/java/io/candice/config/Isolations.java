package io.candice.config;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-19
 */
public interface Isolations {

    int READ_UNCOMMITTED = 1;
    int READ_COMMITTED = 2;
    int REPEATED_READ = 3;
    int SERIALIZABLE = 4;
}
