package io.candice.parser.ast.expression.primary.function.string;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class ConcatWs extends FunctionExpression {
    public ConcatWs(List<Expression> arguments) {
        super("CONCAT_WS", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new ConcatWs(arguments);
    }

}
