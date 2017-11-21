package io.candice.parser.ast.expression.primary.function.misc;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class ReleaseLock extends FunctionExpression {
    public ReleaseLock(List<Expression> arguments) {
        super("RELEASE_LOCK", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new ReleaseLock(arguments);
    }

}
