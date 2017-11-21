package io.candice.parser.ast.expression.primary.function.arithmetic;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Sign extends FunctionExpression {
    public Sign(List<Expression> arguments) {
        super("SIGN", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Sign(arguments);
    }

}
