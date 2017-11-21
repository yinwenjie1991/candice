package io.candice.parser.ast.expression.primary.function.comparison;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Interval extends FunctionExpression {
    public Interval(List<Expression> arguments) {
        super("INTERVAL", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Interval(arguments);
    }

}
