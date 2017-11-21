package io.candice.parser.ast.expression.primary.function.misc;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Default extends FunctionExpression {
    public Default(List<Expression> arguments) {
        super("DEFAULT", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Default(arguments);
    }

}
