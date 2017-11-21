package io.candice.parser.ast.expression.primary.function.misc;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class InetNtoa extends FunctionExpression {
    public InetNtoa(List<Expression> arguments) {
        super("INET_NTOA", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new InetNtoa(arguments);
    }

}
