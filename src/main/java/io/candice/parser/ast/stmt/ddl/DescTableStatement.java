package io.candice.parser.ast.stmt.ddl;

import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.ast.stmt.SQLStatement;
import io.candice.parser.visitor.SQLASTVisitor;

public class DescTableStatement implements SQLStatement {
    private final Identifier table;

    public DescTableStatement(Identifier table) {
        if (table == null) throw new IllegalArgumentException("table is null for desc table");
        this.table = table;
    }

    public Identifier getTable() {
        return table;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
