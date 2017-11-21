package io.candice.parser.ast.expression.comparison;

import io.candice.parser.ast.expression.BinaryOperatorExpression;
import io.candice.parser.ast.expression.Expression;
import io.candice.parser.visitor.SQLASTVisitor;

public class ComparisionNotEqualsExpression extends BinaryOperatorExpression {
    public ComparisionNotEqualsExpression(Expression leftOprand, Expression rightOprand) {
        super(leftOprand, rightOprand, PRECEDENCE_COMPARISION);
    }

    @Override
    public String getOperator() {
        return "!=";
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
