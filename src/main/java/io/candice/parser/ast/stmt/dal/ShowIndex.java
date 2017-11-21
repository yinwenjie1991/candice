package io.candice.parser.ast.stmt.dal;

import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.visitor.SQLASTVisitor;

public class ShowIndex extends DALShowStatement {
    public static enum Type {
        INDEX,
        INDEXES,
        KEYS
    }

    private final Type type;
    private final Identifier table;

    public ShowIndex(Type type, Identifier table, Identifier database) {
        this.table = table;
        this.table.setParent(database);
        this.type = type;
    }

    public ShowIndex(Type type, Identifier table) {
        this.table = table;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Identifier getTable() {
        return table;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
