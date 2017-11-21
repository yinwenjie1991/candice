package io.candice.parser.ast.expression.comparison;

import io.candice.parser.ast.expression.BinaryOperatorExpression;
import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.ReplacableExpression;
import io.candice.parser.ast.expression.misc.InExpressionList;
import io.candice.parser.ast.expression.misc.QueryExpression;
import io.candice.parser.visitor.SQLASTVisitor;

public class InExpression extends BinaryOperatorExpression implements ReplacableExpression {
    private final boolean not;

    public InExpression(boolean not, Expression leftOprand, Expression rightOprand) {
        super(leftOprand, rightOprand, PRECEDENCE_COMPARISION);
        this.not = not;
    }

    public boolean isNot() {
        return not;
    }

    public InExpressionList getInExpressionList() {
        if (rightOprand instanceof InExpressionList) {
            return (InExpressionList) rightOprand;
        }
        return null;
    }

    public QueryExpression getQueryExpression() {
        if (rightOprand instanceof QueryExpression) {
            return (QueryExpression) rightOprand;
        }
        return null;
    }

    @Override
    public String getOperator() {
        return not ? "NOT IN" : "IN";
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
