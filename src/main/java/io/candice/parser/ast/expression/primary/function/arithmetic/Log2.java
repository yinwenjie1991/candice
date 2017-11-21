package io.candice.parser.ast.expression.primary.function.arithmetic;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Log2 extends FunctionExpression {
    public Log2(List<Expression> arguments) {
        super("LOG2", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Log2(arguments);
    }

}
