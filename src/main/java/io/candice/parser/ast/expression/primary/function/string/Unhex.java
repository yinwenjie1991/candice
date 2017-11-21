package io.candice.parser.ast.expression.primary.function.string;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Unhex extends FunctionExpression {
    public Unhex(List<Expression> arguments) {
        super("UNHEX", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Unhex(arguments);
    }

}
