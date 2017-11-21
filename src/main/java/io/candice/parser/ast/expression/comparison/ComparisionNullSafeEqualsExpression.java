package io.candice.parser.ast.expression.comparison;

import io.candice.parser.ast.expression.BinaryOperatorExpression;
import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.ReplacableExpression;
import io.candice.parser.ast.expression.primary.literal.LiteralBoolean;
import io.candice.parser.util.ExprEvalUtils;
import io.candice.parser.util.Pair;
import io.candice.parser.visitor.SQLASTVisitor;

import java.util.Map;

public class ComparisionNullSafeEqualsExpression extends BinaryOperatorExpression implements ReplacableExpression {
    public ComparisionNullSafeEqualsExpression(Expression leftOprand, Expression rightOprand) {
        super(leftOprand, rightOprand, PRECEDENCE_COMPARISION);
    }

    @Override
    public String getOperator() {
        return "<=>";
    }

    @Override
    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        Object left = leftOprand.evaluation(parameters);
        Object right = rightOprand.evaluation(parameters);
        if (left == UNEVALUATABLE || right == UNEVALUATABLE) return UNEVALUATABLE;
        if (left == null) return right == null ? LiteralBoolean.TRUE : LiteralBoolean.FALSE;
        if (right == null) return LiteralBoolean.FALSE;
        if (left instanceof Number || right instanceof Number) {
            Pair<Number, Number> pair = ExprEvalUtils.convertNum2SameLevel(left, right);
            left = pair.getKey();
            right = pair.getValue();
        }
        return left.equals(right) ? LiteralBoolean.TRUE : LiteralBoolean.FALSE;
    }

    private Expression replaceExpr;

    public void setReplaceExpr(Expression replaceExpr) {
        this.replaceExpr = replaceExpr;
    }

    public void clearReplaceExpr() {
        this.replaceExpr = null;
    }

    public void accept(SQLASTVisitor visitor) {
        if (replaceExpr == null) visitor.visit(this);
        else replaceExpr.accept(visitor);
    }
}
