package io.candice.parser.ast.expression.primary.function.string;


import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Concat extends FunctionExpression {
    public Concat(List<Expression> arguments) {
        super("CONCAT", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Concat(arguments);
    }

}
