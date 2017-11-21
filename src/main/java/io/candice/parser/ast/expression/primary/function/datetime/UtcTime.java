package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class UtcTime extends FunctionExpression {
    public UtcTime(List<Expression> arguments) {
        super("UTC_TIME", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new UtcTime(arguments);
    }

}
