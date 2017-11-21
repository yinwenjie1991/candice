package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class PeriodDiff extends FunctionExpression {
    public PeriodDiff(List<Expression> arguments) {
        super("PERIOD_DIFF", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new PeriodDiff(arguments);
    }

}
