package io.candice.parser.ast.expression.primary.function.datetime;


import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class FromUnixtime extends FunctionExpression {
    public FromUnixtime(List<Expression> arguments) {
        super("FROM_UNIXTIME", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new FromUnixtime(arguments);
    }

}
