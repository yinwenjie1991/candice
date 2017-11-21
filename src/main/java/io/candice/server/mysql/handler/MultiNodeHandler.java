package io.candice.server.mysql.handler;

import io.candice.config.ErrorCode;
import io.candice.net.connection.MySQLConnection;
import io.candice.net.mysql.ErrorPacket;
import io.candice.server.session.NonBlockingSession;
import io.candice.util.StringUtil;

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

    private int nodeCount;
    private Runnable terminateCallBack;

    @Override
    public void terminate(Runnable terminateCallBack) {
        boolean zeroReached = false;
        lock.lock();
        try {
            if (nodeCount > 0) {
                this.terminateCallBack = terminateCallBack;
            } else {
                zeroReached = true;
            }
        } finally {
            lock.unlock();
        }
        if (zeroReached) {
            terminateCallBack.run();
        }
    }

    protected void decrementCountToZero() {
        Runnable callback;
        lock.lock();
        try {
            nodeCount = 0;
            callback = this.terminateCallBack;
            this.terminateCallBack = null;
        } finally {
            lock.unlock();
        }
        if (callback != null) {
            callback.run();
        }
    }

    protected boolean decrementCountBy(int finished) {
        boolean zeroReached = false;
        Runnable callback = null;
        lock.lock();
        try {
            if (zeroReached = --nodeCount == 0) {
                callback = this.terminateCallBack;
                this.terminateCallBack = null;
            }
        } finally {
            lock.unlock();
        }
        if (zeroReached && callback != null) {
            callback.run();
        }
        return zeroReached;
    }

    protected void reset(int initCount) {
        nodeCount = initCount;
        isFail.set(false);
        error = null;
        packetId = 0;
    }

    protected void backendConnError(MySQLConnection conn, String errMsg) {
        ErrorPacket err = new ErrorPacket();
        err.packetId = 1;// ERROR_PACKET
        err.errno = ErrorCode.ER_YES;
        err.message = StringUtil.encode(errMsg, session.getSource().getCharset());
        backendConnError(conn, err);
    }

    protected void backendConnError(MySQLConnection conn, ErrorPacket err) {
        conn.setRunning(false);
        lock.lock();
        try {
            if (error == null) {
                error = err;
            }
        } finally {
            lock.unlock();
        }
        isFail.set(true);
        if (decrementCountBy(1)) {
            notifyError();
        }
    }

    protected void notifyError() {
        recycleResources();
        byte errPacketId = ++packetId;
        ErrorPacket err;
        session.clearConnections();
        //session.getSource().setTxInterrupt();
        err = error;
        if (err == null) {
            err = new ErrorPacket();
            err.packetId = errPacketId;
            err.errno = ErrorCode.ER_YES;
            err.message = StringUtil.encode("unknown error", session.getSource().getCharset());
        }
        err.packetId = errPacketId;
        err.write(session.getSource().getChannelHandlerContext());
    }

    protected void notifyError(byte errPacketId) {
        recycleResources();
        ErrorPacket err;
        session.clearConnections();
        //session.getSource().setTxInterrupt();
        err = error;
        if (err == null) {
            err = new ErrorPacket();
            err.packetId = errPacketId;
            err.errno = ErrorCode.ER_YES;
            err.message = StringUtil.encode("unknown error", session.getSource().getCharset());
        }
        err.packetId = errPacketId;
        err.write(session.getSource().getChannelHandlerContext());
    }

    protected void recycleResources() {
    }
}
