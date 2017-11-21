package io.candice.parser.ast.expression.primary.function.datetime;


import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class FromDays extends FunctionExpression {
    public FromDays(List<Expression> arguments) {
        super("FROM_DAYS", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new FromDays(arguments);
    }

}
