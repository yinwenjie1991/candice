package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class ConvertTz extends FunctionExpression {
    public ConvertTz(List<Expression> arguments) {
        super("CONVERT_TZ", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new ConvertTz(arguments);
    }

}
