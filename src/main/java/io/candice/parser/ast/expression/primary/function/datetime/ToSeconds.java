package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class ToSeconds extends FunctionExpression {
    public ToSeconds(List<Expression> arguments) {
        super("TO_SECONDS", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new ToSeconds(arguments);
    }

}
