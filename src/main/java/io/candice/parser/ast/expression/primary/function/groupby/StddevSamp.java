package io.candice.parser.ast.expression.primary.function.groupby;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class StddevSamp extends FunctionExpression {
    public StddevSamp(List<Expression> arguments) {
        super("STDDEV_SAMP", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new StddevSamp(arguments);
    }

}
