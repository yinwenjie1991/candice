package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;


public class DateAdd extends FunctionExpression {
    public DateAdd(List<Expression> arguments) {
        super("DATE_ADD", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new DateAdd(arguments);
    }

}
