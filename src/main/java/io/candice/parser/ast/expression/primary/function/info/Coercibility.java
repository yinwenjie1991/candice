package io.candice.parser.ast.expression.primary.function.info;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Coercibility extends FunctionExpression {
    public Coercibility(List<Expression> arguments) {
        super("COERCIBILITY", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Coercibility(arguments);
    }

}
