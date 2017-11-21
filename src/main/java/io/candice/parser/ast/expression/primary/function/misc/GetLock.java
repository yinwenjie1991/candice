package io.candice.parser.ast.expression.primary.function.misc;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class GetLock extends FunctionExpression {
    public GetLock(List<Expression> arguments) {
        super("GET_LOCK", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new GetLock(arguments);
    }

}
