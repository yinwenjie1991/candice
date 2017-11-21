package io.candice.parser.ast.expression.logical;

import io.candice.parser.ast.expression.BinaryOperatorExpression;
import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.literal.LiteralBoolean;
import io.candice.parser.util.ExprEvalUtils;
import io.candice.parser.visitor.SQLASTVisitor;

import java.util.Map;

public class LogicalXORExpression extends BinaryOperatorExpression {
    public LogicalXORExpression(Expression left, Expression right) {
        super(left, right, PRECEDENCE_LOGICAL_XOR);
    }

    @Override
    public String getOperator() {
        return "XOR";
    }

    @Override
    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        Object left = leftOprand.evaluation(parameters);
        Object right = rightOprand.evaluation(parameters);
        if (left == null || right == null) return null;
        if (left == UNEVALUATABLE || right == UNEVALUATABLE) return UNEVALUATABLE;
        boolean b1 = ExprEvalUtils.obj2bool(left);
        boolean b2 = ExprEvalUtils.obj2bool(right);
        return b1 != b2 ? LiteralBoolean.TRUE : LiteralBoolean.FALSE;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
