package io.candice.parser.ast.expression.primary.function.string;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Repeat extends FunctionExpression {
    public Repeat(List<Expression> arguments) {
        super("REPEAT", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Repeat(arguments);
    }

}
