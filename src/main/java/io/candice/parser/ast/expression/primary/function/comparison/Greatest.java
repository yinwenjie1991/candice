package io.candice.parser.ast.expression.primary.function.comparison;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Greatest extends FunctionExpression {
    public Greatest(List<Expression> arguments) {
        super("GREATEST", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Greatest(arguments);
    }

}
