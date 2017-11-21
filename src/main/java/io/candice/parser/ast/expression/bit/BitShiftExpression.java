package io.candice.parser.ast.expression.bit;

import io.candice.parser.ast.expression.BinaryOperatorExpression;
import io.candice.parser.ast.expression.Expression;
import io.candice.parser.visitor.SQLASTVisitor;

public class BitShiftExpression extends BinaryOperatorExpression {
    private final boolean negative;

    /**
     * @param negative true if right shift
     */
    public BitShiftExpression(boolean negative, Expression leftOprand, Expression rightOprand) {
        super(leftOprand, rightOprand, PRECEDENCE_BIT_SHIFT);
        this.negative = negative;
    }

    public boolean isRightShift() {
        return negative;
    }

    @Override
    public String getOperator() {
        return negative ? ">>" : "<<";
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
