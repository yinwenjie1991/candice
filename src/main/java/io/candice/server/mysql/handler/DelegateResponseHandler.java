package io.candice.server.mysql.handler;

import io.candice.net.connection.MySQLConnection;

import java.util.List;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-16
 */
public class DelegateResponseHandler implements ResponseHandler {

    private final ResponseHandler handler;

    public DelegateResponseHandler(ResponseHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("delegate is null!");
        }
        this.handler = handler;
    }

    @Override
    public void connectionAcquired(MySQLConnection conn) {
        handler.connectionAcquired(conn);
    }

    @Override
    public void connectionError(Throwable e, MySQLConnection conn) {
        handler.connectionError(e, conn);
    }

    @Override
    public void errorResponse(byte[] err, MySQLConnection conn) {
        handler.errorResponse(err, conn);
    }

    @Override
    public void okResponse(byte[] ok, MySQLConnection conn) {
        handler.okResponse(ok, conn);
    }

    @Override
    public void fieldEofResponse(byte[] header, List<byte[]> fields, byte[] eof, MySQLConnection conn) {
        handler.fieldEofResponse(header, fields, eof, conn);
    }

    @Override
    public void rowResponse(byte[] row, MySQLConnection conn) {
        handler.rowResponse(row, conn);
    }

    @Override
    public void rowEofResponse(byte[] eof, MySQLConnection conn) {
        handler.rowEofResponse(eof, conn);
    }
}
