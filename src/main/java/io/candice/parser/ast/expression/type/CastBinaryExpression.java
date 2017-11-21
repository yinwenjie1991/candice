package io.candice.parser.ast.expression.type;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.UnaryOperatorExpression;

/**
 * <code>'BINARY' higherExpr</code>
 *
 */
public class CastBinaryExpression extends UnaryOperatorExpression {
    public CastBinaryExpression(Expression operand) {
        super(operand, PRECEDENCE_BINARY);
    }

    @Override
    public String getOperator() {
        return "BINARY";
    }

}
