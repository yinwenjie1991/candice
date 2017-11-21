package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Curtime extends FunctionExpression {
    public Curtime() {
        super("CURTIME", null);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Curtime();
    }

}
