package io.candice.parser.ast.expression.primary.function.encryption;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;


public class Encode extends FunctionExpression {
    public Encode(List<Expression> arguments) {
        super("ENCODE", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Encode(arguments);
    }

}
