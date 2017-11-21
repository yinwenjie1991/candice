package io.candice.parser.ast.expression.primary.function.encryption;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;


public class Uncompress extends FunctionExpression {
    public Uncompress(List<Expression> arguments) {
        super("UNCOMPRESS", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Uncompress(arguments);
    }

}
