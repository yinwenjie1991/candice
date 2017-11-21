package io.candice.parser.ast.expression.primary.function.string;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class BitLength extends FunctionExpression {
    public BitLength(List<Expression> arguments) {
        super("BIT_LENGTH", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new BitLength(arguments);
    }

}
