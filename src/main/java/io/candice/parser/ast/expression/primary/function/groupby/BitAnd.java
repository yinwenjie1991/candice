package io.candice.parser.ast.expression.primary.function.groupby;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class BitAnd extends FunctionExpression {
    public BitAnd(List<Expression> arguments) {
        super("BIT_AND", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new BitAnd(arguments);
    }

}
