package io.candice.parser.ast.stmt.dal;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.visitor.SQLASTVisitor;

public class ShowTableStatus extends DALShowStatement {
    private Identifier database;
    private final String pattern;
    private final Expression where;

    public ShowTableStatus(Identifier database, Expression where) {
        this.database = database;
        this.pattern = null;
        this.where = where;
    }

    public ShowTableStatus(Identifier database, String pattern) {
        this.database = database;
        this.pattern = pattern;
        this.where = null;
    }

    public ShowTableStatus(Identifier database) {
        this.database = database;
        this.pattern = null;
        this.where = null;
    }

    public void setDatabase(Identifier database) {
        this.database = database;
    }

    public Identifier getDatabase() {
        return database;
    }

    public String getPattern() {
        return pattern;
    }

    public Expression getWhere() {
        return where;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
