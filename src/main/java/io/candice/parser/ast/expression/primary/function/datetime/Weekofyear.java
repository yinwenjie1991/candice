package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Weekofyear extends FunctionExpression {
    public Weekofyear(List<Expression> arguments) {
        super("WEEKOFYEAR", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Weekofyear(arguments);
    }

}
