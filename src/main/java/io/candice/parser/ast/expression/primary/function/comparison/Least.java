package io.candice.parser.ast.expression.primary.function.comparison;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Least extends FunctionExpression {
    public Least(List<Expression> arguments) {
        super("LEAST", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Least(arguments);
    }

}
