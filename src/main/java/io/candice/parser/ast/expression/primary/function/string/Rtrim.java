package io.candice.parser.ast.expression.primary.function.string;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Rtrim extends FunctionExpression {
    public Rtrim(List<Expression> arguments) {
        super("RTRIM", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Rtrim(arguments);
    }

}
