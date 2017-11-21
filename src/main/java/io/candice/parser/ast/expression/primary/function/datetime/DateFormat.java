package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class DateFormat extends FunctionExpression {
    public DateFormat(List<Expression> arguments) {
        super("DATE_FORMAT", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new DateFormat(arguments);
    }

}
