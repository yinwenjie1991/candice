package io.candice.server.mysql.handler;

import io.candice.net.connection.MySQLConnection;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-16
 */
public class GetConnectionHandler implements ResponseHandler{

    private static final Logger LOGGER = Logger.getLogger(GetConnectionHandler.class);


    @Override
    public void connectionAcquired(MySQLConnection conn) {
        LOGGER.info("connected successfuly" + conn);
    }

    @Override
    public void connectionError(Throwable e, MySQLConnection conn) {

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
}
