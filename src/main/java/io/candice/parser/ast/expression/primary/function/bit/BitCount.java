package io.candice.parser.ast.expression.primary.function.bit;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class BitCount extends FunctionExpression {
    public BitCount(List<Expression> arguments) {
        super("BIT_COUNT", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new BitCount(arguments);
    }

}
