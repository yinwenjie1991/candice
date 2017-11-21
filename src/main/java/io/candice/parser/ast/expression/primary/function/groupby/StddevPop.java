package io.candice.parser.ast.expression.primary.function.groupby;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class StddevPop extends FunctionExpression {
    public StddevPop(List<Expression> arguments) {
        super("STDDEV_POP", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new StddevPop(arguments);
    }

}
