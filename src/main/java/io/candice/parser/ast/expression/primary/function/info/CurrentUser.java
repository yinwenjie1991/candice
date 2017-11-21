package io.candice.parser.ast.expression.primary.function.info;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class CurrentUser extends FunctionExpression {
    public CurrentUser() {
        super("CURRENT_USER", null);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new CurrentUser();
    }

}
