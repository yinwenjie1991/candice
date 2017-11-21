package io.candice.parser.ast.expression.bit;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.UnaryOperatorExpression;

public class BitInvertExpression extends UnaryOperatorExpression {
    public BitInvertExpression(Expression operand) {
        super(operand, PRECEDENCE_UNARY_OP);
    }

    @Override
    public String getOperator() {
        return "~";
    }

}
