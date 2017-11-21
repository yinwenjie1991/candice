package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Weekday extends FunctionExpression {
    public Weekday(List<Expression> arguments) {
        super("WEEKDAY", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Weekday(arguments);
    }

}
