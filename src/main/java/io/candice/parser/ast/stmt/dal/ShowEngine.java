package io.candice.parser.ast.stmt.dal;

import io.candice.parser.visitor.SQLASTVisitor;

public class ShowEngine extends DALShowStatement {
    public static enum Type {
        INNODB_STATUS,
        INNODB_MUTEX,
        PERFORMANCE_SCHEMA_STATUS
    }

    private final Type type;

    public ShowEngine(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
