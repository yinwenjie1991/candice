package io.candice.parser.ast.expression.primary;

import io.candice.parser.ast.expression.AbstractExpression;

import java.util.Map;

public abstract class PrimaryExpression extends AbstractExpression {

    public int getPrecedence() {
        return PRECEDENCE_PRIMARY;
    }

    @Override
    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return UNEVALUATABLE;
    }
}
