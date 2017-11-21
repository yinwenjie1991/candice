package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class SecToTime extends FunctionExpression {
    public SecToTime(List<Expression> arguments) {
        super("SEC_TO_TIME", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new SecToTime(arguments);
    }

}
