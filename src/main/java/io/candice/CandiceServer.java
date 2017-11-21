package io.candice;

import org.apache.log4j.Logger;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-14
 */
public class CandiceServer {

    public static final String NAME = "Candice";
    private static final long LOG_WATCH_DELAY = 60000L;
    private static final long TIME_UPDATE_PERIOD = 20L;
    private static final CandiceServer INSTANCE = new CandiceServer();
    private static final Logger LOGGER = Logger.getLogger(CandiceServer.class);


}
