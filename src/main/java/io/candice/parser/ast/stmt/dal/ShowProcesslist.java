package io.candice.parser.ast.stmt.dal;

import io.candice.parser.visitor.SQLASTVisitor;

public class ShowProcesslist extends DALShowStatement {
    private final boolean full;

    public ShowProcesslist(boolean full) {
        this.full = full;
    }

    public boolean isFull() {
        return full;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
