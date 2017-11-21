package io.candice.parser.ast.expression.comparison;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.ReplacableExpression;
import io.candice.parser.ast.expression.TernaryOperatorExpression;
import io.candice.parser.visitor.SQLASTVisitor;

public class BetweenAndExpression extends TernaryOperatorExpression implements ReplacableExpression {
    private final boolean not;

    public BetweenAndExpression(boolean not, Expression comparee, Expression notLessThan,
                                Expression notGreaterThan) {
        super(comparee, notLessThan, notGreaterThan);
        this.not = not;
    }

    public boolean isNot() {
        return not;
    }

    public int getPrecedence() {
        return PRECEDENCE_BETWEEN_AND;
    }

    private Expression replaceExpr;

    public void setReplaceExpr(Expression replaceExpr) {
        this.replaceExpr = replaceExpr;
    }

    public void clearReplaceExpr() {
        this.replaceExpr = null;
    }

    public void accept(SQLASTVisitor visitor) {
        if (replaceExpr == null)
            visitor.visit(this);
        else
            replaceExpr.accept(visitor);
    }
}
