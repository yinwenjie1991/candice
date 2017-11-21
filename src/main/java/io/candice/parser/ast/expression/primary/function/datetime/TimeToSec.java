package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class TimeToSec extends FunctionExpression {
    public TimeToSec(List<Expression> arguments) {
        super("TIME_TO_SEC", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new TimeToSec(arguments);
    }

}
