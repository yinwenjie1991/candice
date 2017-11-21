package io.candice.parser.ast.expression.primary.function.flowctrl;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;


public class Nullif extends FunctionExpression {
    public Nullif(List<Expression> arguments) {
        super("NULLIF", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Nullif(arguments);
    }

}
