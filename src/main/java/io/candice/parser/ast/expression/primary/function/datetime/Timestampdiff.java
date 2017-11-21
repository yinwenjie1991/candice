package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import io.candice.parser.ast.expression.primary.literal.IntervalPrimary;
import io.candice.parser.visitor.SQLASTVisitor;
import java.util.List;

public class Timestampdiff extends FunctionExpression {
    private IntervalPrimary.Unit unit;

    public Timestampdiff(IntervalPrimary.Unit unit, List<Expression> arguments) {
        super("TIMESTAMPDIFF", arguments);
        this.unit = unit;
    }

    public IntervalPrimary.Unit getUnit() {
        return unit;
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        throw new UnsupportedOperationException("function of Timestampdiff has special arguments");
    }

    @Override
    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

}
