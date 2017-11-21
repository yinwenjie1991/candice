package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Timediff extends FunctionExpression {
    public Timediff(List<Expression> arguments) {
        super("TIMEDIFF", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Timediff(arguments);
    }

}
