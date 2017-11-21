package io.candice.parser.ast.expression.comparison;

import io.candice.parser.ast.expression.BinaryOperatorExpression;
import io.candice.parser.ast.expression.Expression;
import io.candice.parser.visitor.SQLASTVisitor;

public class ComparisionGreaterThanOrEqualsExpression extends BinaryOperatorExpression {
    public ComparisionGreaterThanOrEqualsExpression(Expression leftOprand, Expression rightOprand) {
        super(leftOprand, rightOprand, PRECEDENCE_COMPARISION);
    }

    @Override
    public String getOperator() {
        return ">=";
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
