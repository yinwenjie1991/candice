package io.candice.parser.ast.expression.primary.literal;

import io.candice.parser.visitor.SQLASTVisitor;

import java.util.Map;

public class LiteralNull extends Literal {
    @Override
    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return null;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
