package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Now extends FunctionExpression {
    public Now() {
        super("NOW", null);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Now();
    }

}
