package io.candice.parser.ast.stmt.mts;

import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.ast.stmt.SQLStatement;
import io.candice.parser.visitor.SQLASTVisitor;

public class MTSSavepointStatement implements SQLStatement {
    private final Identifier savepoint;

    public MTSSavepointStatement(Identifier savepoint) {
        if (savepoint == null) throw new IllegalArgumentException("savepoint is null");
        this.savepoint = savepoint;
    }

    public Identifier getSavepoint() {
        return savepoint;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

}
