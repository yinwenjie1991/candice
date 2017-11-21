package io.candice.parser.ast.expression.primary.function.misc;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class UuidShort extends FunctionExpression {
    public UuidShort(List<Expression> arguments) {
        super("UUID_SHORT", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new UuidShort(arguments);
    }

}
