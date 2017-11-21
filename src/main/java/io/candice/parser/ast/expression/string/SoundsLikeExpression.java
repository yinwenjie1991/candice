package io.candice.parser.ast.expression.string;

import io.candice.parser.ast.expression.BinaryOperatorExpression;
import io.candice.parser.ast.expression.Expression;
import io.candice.parser.visitor.SQLASTVisitor;

/**
 * <code>higherPreExpr 'SOUNDS' 'LIKE' higherPreExpr</code>
 *
 */
public class SoundsLikeExpression extends BinaryOperatorExpression {
    public SoundsLikeExpression(Expression leftOprand, Expression rightOprand) {
        super(leftOprand, rightOprand, PRECEDENCE_COMPARISION);
    }

    @Override
    public String getOperator() {
        return "SOUNDS LIKE";
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
