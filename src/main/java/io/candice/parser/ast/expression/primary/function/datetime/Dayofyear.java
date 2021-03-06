package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Dayofyear extends FunctionExpression {
    public Dayofyear(List<Expression> arguments) {
        super("DAYOFYEAR", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Dayofyear(arguments);
    }

}
