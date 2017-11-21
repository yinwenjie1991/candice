package io.candice.parser.ast.expression.primary.function.groupby;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class BitXor extends FunctionExpression {
    public BitXor(List<Expression> arguments) {
        super("BIT_XOR", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new BitXor(arguments);
    }

}
