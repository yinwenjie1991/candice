package io.candice.parser.ast.stmt.dal;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.visitor.SQLASTVisitor;

public class ShowCharaterSet extends DALShowStatement {
    private final String pattern;
    private final Expression where;

    public ShowCharaterSet(String pattern) {
        this.pattern = pattern;
        this.where = null;
    }

    public String getPattern() {
        return pattern;
    }

    public Expression getWhere() {
        return where;
    }

    public ShowCharaterSet(Expression where) {
        this.pattern = null;
        this.where = where;
    }

    public ShowCharaterSet() {
        this.pattern = null;
        this.where = null;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
