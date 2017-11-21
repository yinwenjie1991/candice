package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class UnixTimestamp extends FunctionExpression {
    public UnixTimestamp(List<Expression> arguments) {
        super("UNIX_TIMESTAMP", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new UnixTimestamp(arguments);
    }

}
