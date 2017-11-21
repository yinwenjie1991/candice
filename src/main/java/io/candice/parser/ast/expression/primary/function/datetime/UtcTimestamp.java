package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class UtcTimestamp extends FunctionExpression {
    public UtcTimestamp(List<Expression> arguments) {
        super("UTC_TIMESTAMP", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new UtcTimestamp(arguments);
    }

}
