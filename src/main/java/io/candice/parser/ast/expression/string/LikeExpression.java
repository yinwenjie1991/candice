package io.candice.parser.ast.expression.string;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.TernaryOperatorExpression;
import io.candice.parser.visitor.SQLASTVisitor;

public class LikeExpression extends TernaryOperatorExpression {
    private final boolean not;

    /**
     * @param escape null is no ESCAPE
     */
    public LikeExpression(boolean not, Expression comparee, Expression pattern, Expression escape) {
        super(comparee, pattern, escape);
        this.not = not;
    }

    public boolean isNot() {
        return not;
    }

    public int getPrecedence() {
        return PRECEDENCE_COMPARISION;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
