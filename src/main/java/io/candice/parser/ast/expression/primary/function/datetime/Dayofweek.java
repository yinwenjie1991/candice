package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;


public class Dayofweek extends FunctionExpression {
    public Dayofweek(List<Expression> arguments) {
        super("DAYOFWEEK", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Dayofweek(arguments);
    }

}
