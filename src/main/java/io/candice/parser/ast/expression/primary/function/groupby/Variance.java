package io.candice.parser.ast.expression.primary.function.groupby;


import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Variance extends FunctionExpression {
    public Variance(List<Expression> arguments) {
        super("VARIANCE", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Variance(arguments);
    }

}
