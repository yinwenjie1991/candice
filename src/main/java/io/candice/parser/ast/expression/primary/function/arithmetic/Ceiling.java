package io.candice.parser.ast.expression.primary.function.arithmetic;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Ceiling extends FunctionExpression {
    public Ceiling(List<Expression> arguments) {
        super("CEILING", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Ceiling(arguments);
    }

}
