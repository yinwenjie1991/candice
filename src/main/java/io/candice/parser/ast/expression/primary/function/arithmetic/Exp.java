package io.candice.parser.ast.expression.primary.function.arithmetic;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Exp extends FunctionExpression {
    public Exp(List<Expression> arguments) {
        super("EXP", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Exp(arguments);
    }

}
