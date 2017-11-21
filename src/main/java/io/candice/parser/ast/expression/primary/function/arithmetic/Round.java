package io.candice.parser.ast.expression.primary.function.arithmetic;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Round extends FunctionExpression {
    public Round(List<Expression> arguments) {
        super("ROUND", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Round(arguments);
    }

}
