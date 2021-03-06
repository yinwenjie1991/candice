package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Timestamp extends FunctionExpression {
    public Timestamp(List<Expression> arguments) {
        super("TIMESTAMP", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Timestamp(arguments);
    }

}
