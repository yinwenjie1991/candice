package io.candice.parser.ast.stmt.dal;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.visitor.SQLASTVisitor;

public class ShowEvents extends DALShowStatement {
    private Identifier schema;
    private final String pattern;
    private final Expression where;

    public ShowEvents(Identifier schema, String pattern) {
        this.schema = schema;
        this.pattern = pattern;
        this.where = null;
    }

    public ShowEvents(Identifier schema, Expression where) {
        this.schema = schema;
        this.pattern = null;
        this.where = where;
    }

    public ShowEvents(Identifier schema) {
        this.schema = schema;
        this.pattern = null;
        this.where = null;
    }

    public void setSchema(Identifier schema) {
        this.schema = schema;
    }

    public Identifier getSchema() {
        return schema;
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
