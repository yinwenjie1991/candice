package io.candice.parser.ast.stmt.ddl;

import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.visitor.SQLASTVisitor;

public class DDLDropIndexStatement implements DDLStatement {
    private final Identifier indexName;
    private final Identifier table;

    public DDLDropIndexStatement(Identifier indexName, Identifier table) {
        this.indexName = indexName;
        this.table = table;
    }

    public Identifier getIndexName() {
        return indexName;
    }

    public Identifier getTable() {
        return table;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

}
