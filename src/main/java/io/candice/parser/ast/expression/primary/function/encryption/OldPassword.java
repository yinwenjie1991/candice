package io.candice.parser.ast.expression.primary.function.encryption;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class OldPassword extends FunctionExpression {
    public OldPassword(List<Expression> arguments) {
        super("OLD_PASSWORD", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new OldPassword(arguments);
    }

}
