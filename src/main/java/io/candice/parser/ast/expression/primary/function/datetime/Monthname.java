package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Monthname extends FunctionExpression {
    public Monthname(List<Expression> arguments) {
        super("MONTHNAME", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Monthname(arguments);
    }

}
