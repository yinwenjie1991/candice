package io.candice.parser.ast.expression.primary.function.info;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class ConnectionId extends FunctionExpression {
    public ConnectionId(List<Expression> arguments) {
        super("CONNECTION_ID", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new ConnectionId(arguments);
    }

}
