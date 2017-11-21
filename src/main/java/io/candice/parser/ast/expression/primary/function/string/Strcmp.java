package io.candice.parser.ast.expression.primary.function.string;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Strcmp extends FunctionExpression {
    public Strcmp(List<Expression> arguments) {
        super("STRCMP", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Strcmp(arguments);
    }

}
