package io.candice.parser.ast.expression.string;

import io.candice.parser.ast.expression.BinaryOperatorExpression;
import io.candice.parser.ast.expression.Expression;
import io.candice.parser.visitor.SQLASTVisitor;

/**
 * <code>higherPreExpr 'NOT'? ('REGEXP'|'RLIKE') higherPreExp</code>
 *
 */
public class RegexpExpression extends BinaryOperatorExpression {
    private final boolean not;

    public RegexpExpression(boolean not, Expression comparee, Expression pattern) {
        super(comparee, pattern, PRECEDENCE_COMPARISION);
        this.not = not;
    }

    public boolean isNot() {
        return not;
    }

    @Override
    public String getOperator() {
        return not ? "NOT REGEXP" : "REGEXP";
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
