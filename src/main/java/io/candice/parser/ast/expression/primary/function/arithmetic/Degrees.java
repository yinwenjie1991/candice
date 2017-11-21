package io.candice.parser.ast.expression.primary.function.arithmetic;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;


public class Degrees extends FunctionExpression {
    public Degrees(List<Expression> arguments) {
        super("DEGREES", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Degrees(arguments);
    }

}
