package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class UtcDate extends FunctionExpression {
    public UtcDate(List<Expression> arguments) {
        super("UTC_DATE", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new UtcDate(arguments);
    }

}
