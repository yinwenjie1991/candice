package io.candice.parser.ast.expression.primary.function.comparison;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Isnull extends FunctionExpression {
    public Isnull(List<Expression> arguments) {
        super("ISNULL", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Isnull(arguments);
    }

}
