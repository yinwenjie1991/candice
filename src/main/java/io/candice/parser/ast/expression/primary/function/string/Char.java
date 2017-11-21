package io.candice.parser.ast.expression.primary.function.string;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import io.candice.parser.visitor.SQLASTVisitor;

import java.util.List;

public class Char extends FunctionExpression {
    private final String charset;

    public Char(List<Expression> arguments, String charset) {
        super("CHAR", arguments);
        this.charset = charset;
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        throw new UnsupportedOperationException("function of char has special arguments");
    }

    public String getCharset() {
        return charset;
    }

    @Override
    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
