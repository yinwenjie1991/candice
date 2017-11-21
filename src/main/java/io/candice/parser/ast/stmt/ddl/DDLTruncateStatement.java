package io.candice.parser.ast.stmt.ddl;

import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.visitor.SQLASTVisitor;

public class DDLTruncateStatement implements DDLStatement {
    private final Identifier table;

    public DDLTruncateStatement(Identifier table) {
        this.table = table;
    }

    public Identifier getTable() {
        return table;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

}
