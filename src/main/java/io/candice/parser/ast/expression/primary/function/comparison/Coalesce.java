package io.candice.parser.ast.expression.primary.function.comparison;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Coalesce extends FunctionExpression {
    public Coalesce(List<Expression> arguments) {
        super("COALESCE", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Coalesce(arguments);
    }

}
