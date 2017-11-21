package io.candice.parser.ast.expression.primary.function.misc;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class IsUsedLock extends FunctionExpression {
    public IsUsedLock(List<Expression> arguments) {
        super("IS_USED_LOCK", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new IsUsedLock(arguments);
    }

}
