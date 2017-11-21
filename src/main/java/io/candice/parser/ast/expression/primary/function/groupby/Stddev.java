package io.candice.parser.ast.expression.primary.function.groupby;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Stddev extends FunctionExpression {
    public Stddev(List<Expression> arguments) {
        super("STDDEV", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Stddev(arguments);
    }

}
