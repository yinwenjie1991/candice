package io.candice.parser.ast.fragment.tableref;

import io.candice.parser.visitor.SQLASTVisitor;

public class Dual implements TableReference {

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

    public Object removeLastConditionElement() {
        return null;
    }

    public boolean isSingleTable() {
        return true;
    }

    public int getPrecedence() {
        return PRECEDENCE_FACTOR;
    }

}
