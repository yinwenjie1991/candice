package io.candice.parser.ast.expression.primary.function.string;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class FindInSet extends FunctionExpression {
    public FindInSet(List<Expression> arguments) {
        super("FIND_IN_SET", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new FindInSet(arguments);
    }

}
