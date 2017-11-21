package io.candice.parser.ast.expression.primary.function.string;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class MakeSet extends FunctionExpression {
    public MakeSet(List<Expression> arguments) {
        super("MAKE_SET", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new MakeSet(arguments);
    }

}
