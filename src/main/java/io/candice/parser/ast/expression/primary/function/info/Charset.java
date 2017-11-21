package io.candice.parser.ast.expression.primary.function.info;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Charset extends FunctionExpression {
    public Charset(List<Expression> arguments) {
        super("CHARSET", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Charset(arguments);
    }

}
