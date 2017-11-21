package io.candice.parser.ast.stmt.dal;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.fragment.VariableScope;
import io.candice.parser.visitor.SQLASTVisitor;

public class ShowStatus extends DALShowStatement {
    private final VariableScope scope;
    private final String pattern;
    private final Expression where;

    public ShowStatus(VariableScope scope, String pattern) {
        this.scope = scope;
        this.pattern = pattern;
        this.where = null;
    }

    public ShowStatus(VariableScope scope, Expression where) {
        this.scope = scope;
        this.pattern = null;
        this.where = where;
    }

    public ShowStatus(VariableScope scope) {
        this.scope = scope;
        this.pattern = null;
        this.where = null;
    }

    public VariableScope getScope() {
        return scope;
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
