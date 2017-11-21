package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Microsecond extends FunctionExpression {
    public Microsecond(List<Expression> arguments) {
        super("MICROSECOND", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Microsecond(arguments);
    }

}
