package io.candice.parser.ast.expression.arithmeic;

import io.candice.parser.ast.expression.BinaryOperatorExpression;
import io.candice.parser.ast.expression.Expression;
import io.candice.parser.util.BinaryOperandCalculator;
import io.candice.parser.util.ExprEvalUtils;
import io.candice.parser.util.Pair;

import java.util.Map;

public abstract class ArithmeticBinaryOperatorExpression extends BinaryOperatorExpression implements
        BinaryOperandCalculator {
    protected ArithmeticBinaryOperatorExpression(Expression leftOprand, Expression rightOprand,
                                                 int precedence) {
        super(leftOprand, rightOprand, precedence, true);
    }

    @Override
    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        Object left = leftOprand.evaluation(parameters);
        Object right = rightOprand.evaluation(parameters);
        if (left == null || right == null)
            return null;
        if (left == UNEVALUATABLE || right == UNEVALUATABLE)
            return UNEVALUATABLE;
        Pair<Number, Number> pair = ExprEvalUtils.convertNum2SameLevel(left, right);
        return ExprEvalUtils.calculate(this, pair.getKey(), pair.getValue());
    }

}
