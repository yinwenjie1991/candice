package io.candice.parser.ast.stmt.dal;

import io.candice.parser.ast.fragment.Limit;
import io.candice.parser.visitor.SQLASTVisitor;

public class ShowWarnings extends DALShowStatement {
    private final boolean count;
    private final Limit limit;

    public ShowWarnings(boolean count, Limit limit) {
        this.count = count;
        this.limit = limit;
    }

    public boolean isCount() {
        return count;
    }

    public Limit getLimit() {
        return limit;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
