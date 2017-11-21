package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class PeriodAdd extends FunctionExpression {
    public PeriodAdd(List<Expression> arguments) {
        super("PERIOD_ADD", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new PeriodAdd(arguments);
    }

}
