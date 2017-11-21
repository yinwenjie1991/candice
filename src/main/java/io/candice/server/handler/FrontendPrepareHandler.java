package io.candice.net.handler.front.prepare;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-17
 */
public interface FrontendPrepareHandler {

    void prepare(String sql);

    void execute(byte[] data);

    void close();

}
