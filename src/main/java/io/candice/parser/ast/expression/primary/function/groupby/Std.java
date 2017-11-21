package io.candice.parser.ast.expression.primary.function.groupby;


import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Std extends FunctionExpression {
    public Std(List<Expression> arguments) {
        super("STD", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Std(arguments);
    }

}
