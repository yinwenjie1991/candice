package io.candice.parser.ast.expression.primary.function.misc;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class InetAton extends FunctionExpression {
    public InetAton(List<Expression> arguments) {
        super("INET_ATON", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new InetAton(arguments);
    }

}
