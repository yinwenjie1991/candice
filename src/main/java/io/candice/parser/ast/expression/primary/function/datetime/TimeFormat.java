package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;


public class TimeFormat extends FunctionExpression {
    public TimeFormat(List<Expression> arguments) {
        super("TIME_FORMAT", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new TimeFormat(arguments);
    }

}
