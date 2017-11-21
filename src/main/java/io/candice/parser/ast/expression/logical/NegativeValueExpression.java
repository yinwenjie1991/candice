package io.candice.parser.ast.expression.logical;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.UnaryOperatorExpression;
import io.candice.parser.ast.expression.primary.literal.LiteralBoolean;
import io.candice.parser.util.ExprEvalUtils;

import java.util.Map;

public class NegativeValueExpression extends UnaryOperatorExpression {
    public NegativeValueExpression(Expression operand) {
        super(operand, PRECEDENCE_UNARY_OP);
    }

    @Override
    public String getOperator() {
        return "!";
    }

    @Override
    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        Object operand = getOperand().evaluation(parameters);
        if (operand == null) return null;
        if (operand == UNEVALUATABLE) return UNEVALUATABLE;
        boolean bool = ExprEvalUtils.obj2bool(operand);
        return bool ? LiteralBoolean.FALSE : LiteralBoolean.TRUE;
    }

}
