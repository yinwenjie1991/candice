package io.candice.parser.ast.stmt.dal;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.visitor.SQLASTVisitor;

public class ShowGrants extends DALShowStatement {
    private final Expression user;

    public ShowGrants(Expression user) {
        this.user = user;
    }

    public ShowGrants() {
        this.user = null;
    }

    public Expression getUser() {
        return user;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

}
