package io.candice.parser.ast.expression.primary.function.string;


import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Ltrim extends FunctionExpression {
    public Ltrim(List<Expression> arguments) {
        super("LTRIM", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Ltrim(arguments);
    }

}
