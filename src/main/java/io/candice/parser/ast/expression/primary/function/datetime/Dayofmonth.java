package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Dayofmonth extends FunctionExpression {
    public Dayofmonth(List<Expression> arguments) {
        super("DAYOFMONTH", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Dayofmonth(arguments);
    }

}
