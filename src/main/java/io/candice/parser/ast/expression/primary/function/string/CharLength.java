package io.candice.parser.ast.expression.primary.function.string;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class CharLength extends FunctionExpression {
    public CharLength(List<Expression> arguments) {
        super("CHAR_LENGTH", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new CharLength(arguments);
    }

}
