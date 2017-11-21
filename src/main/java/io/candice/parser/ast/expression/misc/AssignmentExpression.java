package io.candice.parser.ast.expression.misc;

import io.candice.parser.ast.expression.BinaryOperatorExpression;
import io.candice.parser.ast.expression.Expression;
import io.candice.parser.visitor.SQLASTVisitor;

public class AssignmentExpression extends BinaryOperatorExpression {
    public AssignmentExpression(Expression left, Expression right) {
        super(left, right, Expression.PRECEDENCE_ASSIGNMENT, false);
    }

    public String getOperator() {
        return ":=";
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
