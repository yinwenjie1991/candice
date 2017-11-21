package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Yearweek extends FunctionExpression {
    public Yearweek(List<Expression> arguments) {
        super("YEARWEEK", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Yearweek(arguments);
    }

}
