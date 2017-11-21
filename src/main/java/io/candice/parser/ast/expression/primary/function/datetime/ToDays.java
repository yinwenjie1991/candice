package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class ToDays extends FunctionExpression {
    public ToDays(List<Expression> arguments) {
        super("TO_DAYS", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new ToDays(arguments);
    }

}
