package io.candice.parser.ast.stmt.dal;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.visitor.SQLASTVisitor;

public class ShowCollation extends DALShowStatement {
    private final String pattern;
    private final Expression where;

    public ShowCollation(Expression where) {
        this.pattern = null;
        this.where = where;
    }

    public ShowCollation(String pattern) {
        this.pattern = pattern;
        this.where = null;
    }

    public ShowCollation() {
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
