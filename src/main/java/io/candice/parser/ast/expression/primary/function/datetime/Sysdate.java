package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Sysdate extends FunctionExpression {
    public Sysdate(List<Expression> arguments) {
        super("SYSDATE", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Sysdate(arguments);
    }

}
