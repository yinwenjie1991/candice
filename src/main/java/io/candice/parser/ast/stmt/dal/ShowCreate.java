package io.candice.parser.ast.stmt.dal;

import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.visitor.SQLASTVisitor;

public class ShowCreate extends DALShowStatement {
    /** enum name must equals to real sql string */
    public static enum Type {
        DATABASE,
        EVENT,
        FUNCTION,
        PROCEDURE,
        TABLE,
        TRIGGER,
        VIEW
    }

    private final Type type;
    private final Identifier id;

    public ShowCreate(Type type, Identifier id) {
        this.type = type;
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public Identifier getId() {
        return id;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
