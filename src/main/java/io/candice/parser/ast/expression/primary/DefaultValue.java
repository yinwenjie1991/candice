package io.candice.parser.ast.expression.primary;

import io.candice.parser.visitor.SQLASTVisitor;

public class DefaultValue extends PrimaryExpression {

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
