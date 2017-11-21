package io.candice.server.mysql.handler;

import io.candice.net.mysql.ErrorPacket;
import io.candice.server.session.NonBlockingSession;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-19
 */
public abstract class MultiNodeHandler implements ResponseHandler, Terminatable{

    protected final ReentrantLock lock = new ReentrantLock();
    protected final NonBlockingSession session;
    protected AtomicBoolean isFail = new AtomicBoolean(false);
    private ErrorPacket error;
    protected byte packetId;

    public MultiNodeHandler(NonBlockingSession session) {
        if (session == null) {
            throw new IllegalArgumentException("session is null!");
        }
        this.session = session;
    }
}
