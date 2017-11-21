package io.candice.parser.ast.fragment.tableref;

import io.candice.parser.ast.expression.misc.QueryExpression;
import io.candice.parser.visitor.SQLASTVisitor;

public class SubqueryFactor extends AliasableTableReference {
    private final QueryExpression subquery;

    public SubqueryFactor(QueryExpression subquery, String alias) {
        super(alias);
        if (alias == null) throw new IllegalArgumentException("alias is required for subquery factor");
        if (subquery == null) throw new IllegalArgumentException("subquery is null");
        this.subquery = subquery;
    }

    public QueryExpression getSubquery() {
        return subquery;
    }

    public Object removeLastConditionElement() {
        return null;
    }

    public boolean isSingleTable() {
        return false;
    }

    public int getPrecedence() {
        return TableReference.PRECEDENCE_FACTOR;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
