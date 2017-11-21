package io.candice.parser.ast.expression.primary.function.misc;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Uuid extends FunctionExpression {
    public Uuid(List<Expression> arguments) {
        super("UUID", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Uuid(arguments);
    }

}
