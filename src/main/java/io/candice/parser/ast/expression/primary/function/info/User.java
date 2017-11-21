package io.candice.parser.ast.expression.primary.function.info;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class User extends FunctionExpression {
    public User(List<Expression> arguments) {
        super("USER", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new User(arguments);
    }

}
