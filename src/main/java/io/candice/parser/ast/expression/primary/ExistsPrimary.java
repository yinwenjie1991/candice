package io.candice.parser.ast.expression.primary;

import io.candice.parser.ast.expression.misc.QueryExpression;
import io.candice.parser.visitor.SQLASTVisitor;

public class ExistsPrimary extends PrimaryExpression {
    private final QueryExpression subquery;

    public ExistsPrimary(QueryExpression subquery) {
        if (subquery == null) throw new IllegalArgumentException("subquery is null for EXISTS expression");
        this.subquery = subquery;
    }

    /**
     * @return never null
     */
    public QueryExpression getSubquery() {
        return subquery;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
