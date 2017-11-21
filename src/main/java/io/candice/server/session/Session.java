package io.candice.server.session;

import io.candice.net.connection.FrontendConnection;
import io.candice.route.RouteResultset;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-02
 */
public interface Session {

    /**
     * 取得源端连接
     */
    FrontendConnection getSource();

    /**
     * 取得当前目标端数量
     */
    int getTargetCount();

    /**
     * 开启一个会话执行
     */
    void execute(RouteResultset rrs, String sql, int type);

    /**
     * 提交一个会话执行
     */
    void commit();

    /**
     * 回滚一个会话执行
     */
    void rollback();

    /**
     * 取消一个正在执行中的会话
     *
     * @param sponsor
     *            如果发起者为null，则表示由自己发起。
     */
    void cancel(FrontendConnection sponsor);

    /**
     * 终止会话，必须在关闭源端连接后执行该方法。
     */
    void terminate();


}
