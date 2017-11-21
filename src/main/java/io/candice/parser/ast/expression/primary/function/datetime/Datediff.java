package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;


public class Datediff extends FunctionExpression {
    public Datediff(List<Expression> arguments) {
        super("DATEDIFF", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Datediff(arguments);
    }

}
