package io.candice.parser.ast.expression.primary.function.encryption;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Encrypt extends FunctionExpression {
    public Encrypt(List<Expression> arguments) {
        super("ENCRYPT", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Encrypt(arguments);
    }

}
