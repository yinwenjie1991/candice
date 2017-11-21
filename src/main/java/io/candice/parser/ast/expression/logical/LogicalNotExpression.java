package io.candice.parser.ast.expression.logical;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.UnaryOperatorExpression;
import io.candice.parser.ast.expression.primary.literal.LiteralBoolean;
import io.candice.parser.util.ExprEvalUtils;

import java.util.Map;

public class LogicalNotExpression extends UnaryOperatorExpression {
    public LogicalNotExpression(Expression operand) {
        super(operand, PRECEDENCE_LOGICAL_NOT);
    }

    @Override
    public String getOperator() {
        return "NOT";
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
