package io.candice.net.handler.backend;

import io.candice.net.connection.MySQLConnection;
import io.candice.net.mysql.EOFPacket;
import io.candice.net.mysql.ErrorPacket;
import io.candice.net.mysql.OkPacket;
import io.candice.util.ByteUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-17
 */
public class BackendMySQLConnectionHandler extends ChannelInboundHandlerAdapter{

    private static final Logger LOGGER = Logger.getLogger(BackendMySQLConnectionHandler.class);

    private static final int RESULT_STATUS_INIT = 0;
    private static final int RESULT_STATUS_HEADER = 1;
    private static final int RESULT_STATUS_FIELD_EOF = 2;

    private MySQLConnection mySQLConnection;

    private volatile int resultStatus;
    private volatile byte[] header;
    private volatile List<byte[]> fields;

    public BackendMySQLConnectionHandler(MySQLConnection mySQLConnection) {
        this.mySQLConnection = mySQLConnection;
        this.resultStatus = RESULT_STATUS_INIT;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] data = (byte[]) msg;
        switch (resultStatus) {
            case RESULT_STATUS_INIT:
                switch (data[4]) {
                    case OkPacket.FIELD_COUNT:
                        if(LOGGER.isDebugEnabled()){
                            LOGGER.debug("RESULT_STATUS_INIT ok ..");
                        }
                        handleOkPacket(data);
                        break;
                    case ErrorPacket.FIELD_COUNT:
                        if(LOGGER.isDebugEnabled()){
                            LOGGER.debug("FIELD_COUNT error ..");
                        }
                        handleErrorPacket(data);
                        break;
                    default:
                        resultStatus = RESULT_STATUS_HEADER;
                        header = data;
                        fields = new ArrayList<byte[]>((int) ByteUtil.readLength(data, 4));
                }
                break;
            case RESULT_STATUS_HEADER:
                switch (data[4]) {
                    case ErrorPacket.FIELD_COUNT:
                        if(LOGGER.isDebugEnabled()){
                            LOGGER.debug("RESULT_STATUS_HEADER error ..");
                        }
                        resultStatus = RESULT_STATUS_INIT;
                        handleErrorPacket(data);
                        break;
                    case EOFPacket.FIELD_COUNT:
                        if(LOGGER.isDebugEnabled()){
                            LOGGER.debug("RESULT_STATUS_HEADER field eof ..");
                        }
                        resultStatus = RESULT_STATUS_FIELD_EOF;
                        handleFieldEofPacket(data);
                        break;
                    default:
                        fields.add(data);
                }
                break;
            case RESULT_STATUS_FIELD_EOF:
                switch (data[4]) {
                    case ErrorPacket.FIELD_COUNT:
                        if(LOGGER.isDebugEnabled()){
                            LOGGER.debug("RESULT_STATUS_FIELD_EOF error ..");
                        }
                        resultStatus = RESULT_STATUS_INIT;
                        handleErrorPacket(data);
                        break;
                    case EOFPacket.FIELD_COUNT:
                        if(LOGGER.isDebugEnabled()){
                            LOGGER.debug("RESULT_STATUS_FIELD_EOF row eof ..");
                        }
                        resultStatus = RESULT_STATUS_INIT;
                        handleRowEofPacket(data);
                        break;
                    default:
                        handleRowPacket(data);
                }
                break;
            default:
                throw new RuntimeException("unknown status!");
        }
        super.channelRead(ctx, msg);
    }

    /**
     * OK数据包处理
     */
    private void handleOkPacket(byte[] data) {
        if (mySQLConnection.getHandler() == null) {
            System.out.println("handler come null");
            throw new RuntimeException("handleOkPacket handler is null");
        }
        mySQLConnection.getHandler().okResponse(data, mySQLConnection);
    }

    /**
     * ERROR数据包处理
     */
    private void handleErrorPacket(byte[] data) {
        if (mySQLConnection.getHandler() == null) {
            System.out.println("handler come null");
            throw new RuntimeException("handleErrorPacket handler is null");
        }
        mySQLConnection.getHandler().errorResponse(data, mySQLConnection);
    }

    /**
     * 字段数据包结束处理
     */
    private void handleFieldEofPacket(byte[] data) {
        if (mySQLConnection.getHandler() == null) {
            System.out.println("handler come null");
            throw new RuntimeException("handleFieldEofPacket handler is null");
        }
        mySQLConnection.getHandler().fieldEofResponse(header, fields, data, mySQLConnection);
    }

    /**
     * 行数据包处理
     */
    private void handleRowPacket(byte[] data) {
        if (mySQLConnection.getHandler() == null) {
            System.out.println("handler come null");
            throw new RuntimeException("handleRowPacket handler is null");
        }
        mySQLConnection.getHandler().rowResponse(data, mySQLConnection);
    }

    /**
     * 行数据包结束处理
     */
    private void handleRowEofPacket(byte[] data) {
        if (mySQLConnection.getHandler() == null) {
            System.out.println("handler come null");
            throw new RuntimeException("handleRowEofPacket handler is null");
        }
        mySQLConnection.getHandler().rowEofResponse(data, mySQLConnection);
    }


}
