package io.candice.parser.recognizer.mysql.syntax;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.ast.stmt.dml.DMLCallStatement;
import io.candice.parser.recognizer.mysql.lexer.MySQLLexer;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static io.candice.parser.recognizer.mysql.MySQLToken.*;

public class MySQLDMLCallParser extends MySQLDMLParser {
    public MySQLDMLCallParser(MySQLLexer lexer, MySQLExprParser exprParser) {
        super(lexer, exprParser);
    }

    public DMLCallStatement call() throws SQLSyntaxErrorException {
        match(KW_CALL);
        Identifier procedure = identifier();
        match(PUNC_LEFT_PAREN);
        if (lexer.token() == PUNC_RIGHT_PAREN) {
            lexer.nextToken();
            return new DMLCallStatement(procedure);
        }
        List<Expression> arguments;
        Expression expr = exprParser.expression();
        switch (lexer.token()) {
        case PUNC_COMMA:
            arguments = new LinkedList<Expression>();
            arguments.add(expr);
            for (; lexer.token() == PUNC_COMMA;) {
                lexer.nextToken();
                expr = exprParser.expression();
                arguments.add(expr);
            }
            match(PUNC_RIGHT_PAREN);
            return new DMLCallStatement(procedure, arguments);
        case PUNC_RIGHT_PAREN:
            lexer.nextToken();
            arguments = new ArrayList<Expression>(1);
            arguments.add(expr);
            return new DMLCallStatement(procedure, arguments);
        default:
            throw err("expect ',' or ')' after first argument of procedure");
        }
    }

}
