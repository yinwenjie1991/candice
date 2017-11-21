package io.candice.parser.ast.expression.primary.function.encryption;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Sha2 extends FunctionExpression {
    public Sha2(List<Expression> arguments) {
        super("SHA2", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Sha2(arguments);
    }

}
