package io.candice.parser.ast.expression.primary.function.misc;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class IsFreeLock extends FunctionExpression {
    public IsFreeLock(List<Expression> arguments) {
        super("IS_FREE_LOCK", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new IsFreeLock(arguments);
    }

}
