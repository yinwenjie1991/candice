package io.candice.server.mysql.handler;

import io.candice.net.connection.MySQLConnection;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-16
 */
public class GetConnectionHandler implements ResponseHandler{

    private static final Logger LOGGER = Logger.getLogger(GetConnectionHandler.class);

    private final CopyOnWriteArrayList<MySQLConnection> successConnections;

    private AtomicInteger finishCount = new AtomicInteger(0);

    private final int total;

    public GetConnectionHandler(CopyOnWriteArrayList<MySQLConnection> successConnections, int total) {
        this.successConnections = successConnections;
        this.total = total;
    }

    @Override
    public void connectionAcquired(MySQLConnection conn) {
        LOGGER.info("connected successfuly" + conn);
        successConnections.add(conn);
        finishCount.incrementAndGet();
        conn.release();
    }

    @Override
    public void connectionError(Throwable e, MySQLConnection conn) {
        LOGGER.warn("connect error " + conn+ e);
        conn.release();
    }

    @Override
    public void errorResponse(byte[] err, MySQLConnection conn) {

    }

    @Override
    public void okResponse(byte[] ok, MySQLConnection conn) {

    }

    @Override
    public void fieldEofResponse(byte[] header, List<byte[]> fields, byte[] eof, MySQLConnection conn) {

    }

    @Override
    public void rowResponse(byte[] row, MySQLConnection conn) {

    }

    @Override
    public void rowEofResponse(byte[] eof, MySQLConnection conn) {

    }

    public boolean finished() {
        return finishCount.get() >= total;
    }

    public String getStatusInfo() {
        return "finished " + finishCount.get() + " success " + successConnections.size()
                + " target count:" + this.total;
    }
}
