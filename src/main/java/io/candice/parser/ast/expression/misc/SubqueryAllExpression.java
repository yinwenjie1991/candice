package io.candice.parser.ast.expression.misc;

import io.candice.parser.ast.expression.UnaryOperatorExpression;

public class SubqueryAllExpression extends UnaryOperatorExpression {
    public SubqueryAllExpression(QueryExpression subquery) {
        super(subquery, PRECEDENCE_PRIMARY);
    }

    @Override
    public String getOperator() {
        return "ALL";
    }

}
