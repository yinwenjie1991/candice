package io.candice.parser.ast.expression.primary.function.string;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Space extends FunctionExpression {
    public Space(List<Expression> arguments) {
        super("SPACE", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Space(arguments);
    }

}
