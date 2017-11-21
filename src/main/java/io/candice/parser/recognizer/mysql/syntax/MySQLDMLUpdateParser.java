package io.candice.parser.recognizer.mysql.syntax;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.ast.fragment.Limit;
import io.candice.parser.ast.fragment.OrderBy;
import io.candice.parser.ast.fragment.tableref.TableReferences;
import io.candice.parser.ast.stmt.dml.DMLUpdateStatement;
import io.candice.parser.recognizer.mysql.lexer.MySQLLexer;
import io.candice.parser.util.Pair;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static io.candice.parser.recognizer.mysql.MySQLToken.*;

public class MySQLDMLUpdateParser extends MySQLDMLParser {
    public MySQLDMLUpdateParser(MySQLLexer lexer, MySQLExprParser exprParser) {
        super(lexer, exprParser);
    }

    /**
     * nothing has been pre-consumed <code><pre>
     * 'UPDATE' 'LOW_PRIORITY'? 'IGNORE'? table_reference
     *   'SET' colName ('='|':=') (expr|'DEFAULT') (',' colName ('='|':=') (expr|'DEFAULT'))*
     *     ('WHERE' cond)?
     *     {singleTable}? => ('ORDER' 'BY' orderBy)?  ('LIMIT' count)?
     * </pre></code>
     */
    public DMLUpdateStatement update() throws SQLSyntaxErrorException {
        match(KW_UPDATE);
        boolean lowPriority = false;
        boolean ignore = false;
        if (lexer.token() == KW_LOW_PRIORITY) {
            lexer.nextToken();
            lowPriority = true;
        }
        if (lexer.token() == KW_IGNORE) {
            lexer.nextToken();
            ignore = true;
        }
        TableReferences tableRefs = tableRefs();
        match(KW_SET);
        List<Pair<Identifier, Expression>> values;
        Identifier col = identifier();
        match(OP_EQUALS, OP_ASSIGN);
        Expression expr = exprParser.expression();
        if (lexer.token() == PUNC_COMMA) {
            values = new LinkedList<Pair<Identifier, Expression>>();
            values.add(new Pair<Identifier, Expression>(col, expr));
            for (; lexer.token() == PUNC_COMMA;) {
                lexer.nextToken();
                col = identifier();
                match(OP_EQUALS, OP_ASSIGN);
                expr = exprParser.expression();
                values.add(new Pair<Identifier, Expression>(col, expr));
            }
        } else {
            values = new ArrayList<Pair<Identifier, Expression>>(1);
            values.add(new Pair<Identifier, Expression>(col, expr));
        }
        Expression where = null;
        if (lexer.token() == KW_WHERE) {
            lexer.nextToken();
            where = exprParser.expression();
        }
        OrderBy orderBy = null;
        Limit limit = null;
        if (tableRefs.isSingleTable()) {
            orderBy = orderBy();
            limit = limit();
        }
        return new DMLUpdateStatement(lowPriority, ignore, tableRefs, values, where, orderBy, limit);
    }
}
