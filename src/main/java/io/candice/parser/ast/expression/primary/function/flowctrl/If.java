package io.candice.parser.ast.expression.primary.function.flowctrl;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class If extends FunctionExpression {
    public If(List<Expression> arguments) {
        super("IF", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new If(arguments);
    }

}
