package io.candice.parser.ast.expression.misc;

import io.candice.parser.ast.expression.UnaryOperatorExpression;

public class SubqueryAnyExpression extends UnaryOperatorExpression {
    public SubqueryAnyExpression(QueryExpression subquery) {
        super(subquery, PRECEDENCE_PRIMARY);
    }

    @Override
    public String getOperator() {
        return "ANY";
    }

}
