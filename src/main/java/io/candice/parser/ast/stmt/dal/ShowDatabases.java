package io.candice.parser.ast.stmt.dal;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.visitor.SQLASTVisitor;

public class ShowDatabases extends DALShowStatement {
    private final String pattern;
    private final Expression where;

    public ShowDatabases(String pattern) {
        super();
        this.pattern = pattern;
        this.where = null;
    }

    public ShowDatabases(Expression where) {
        super();
        this.pattern = null;
        this.where = where;
    }

    public ShowDatabases() {
        super();
        this.pattern = null;
        this.where = null;
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
