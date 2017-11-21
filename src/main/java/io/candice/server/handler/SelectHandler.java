package io.candice.server.handler;

import io.candice.net.connection.ServerConnection;
import io.candice.parser.util.ParseUtil;
import io.candice.server.parser.ServerParse;
import io.candice.server.parser.ServerParseSelect;
import io.candice.server.response.*;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-17
 */
public class SelectHandler {

    public static void handle(String stmt, ServerConnection c, int offs) {

        int offset = offs;

        switch (ServerParseSelect.parse(stmt, offs)) {
            case ServerParseSelect.VERSION_COMMENT:
                SelectVersionComment.response(c);
                break;
            case ServerParseSelect.DATABASE:
                SelectDatabase.response(c);
                break;
            case ServerParseSelect.USER:
                SelectUser.response(c);
                break;
            case ServerParseSelect.VERSION:
                SelectVersion.response(c);
                break;
            case ServerParseSelect.LAST_INSERT_ID:
                loop: for (; offset < stmt.length(); ++offset) {
                    switch (stmt.charAt(offset)) {
                        case ' ':
                            continue;
                        case '/':
                        case '#':
                            offset = ParseUtil.comment(stmt, offset);
                            continue;
                        case 'L':
                        case 'l':
                            break loop;
                    }
                }
                offset = ServerParseSelect.indexAfterLastInsertIdFunc(stmt, offset);
                offset = ServerParseSelect.skipAs(stmt, offset);
                SelectLastInsertId.response(c, stmt, offset);
                break;
            case ServerParseSelect.IDENTITY:
                loop: for (; offset < stmt.length(); ++offset) {
                    switch (stmt.charAt(offset)) {
                        case ' ':
                            continue;
                        case '/':
                        case '#':
                            offset = ParseUtil.comment(stmt, offset);
                            continue;
                        case '@':
                            break loop;
                    }
                }
                int indexOfAtAt = offset;
                offset += 2;
                offset = ServerParseSelect.indexAfterIdentity(stmt, offset);
                String orgName = stmt.substring(indexOfAtAt, offset);
                offset = ServerParseSelect.skipAs(stmt, offset);
                SelectIdentity.response(c, stmt, offset, orgName);
                break;
            default:
                c.execute(stmt, ServerParse.SELECT);
        }
    }
}
