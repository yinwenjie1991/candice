package io.candice.parser.ast.expression.primary;

import io.candice.parser.visitor.SQLASTVisitor;

public class Wildcard extends Identifier {
    public Wildcard(Identifier parent) {
        super(parent, "*", "*");
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
