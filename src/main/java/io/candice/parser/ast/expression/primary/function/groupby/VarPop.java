package io.candice.parser.ast.expression.primary.function.groupby;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class VarPop extends FunctionExpression {
    public VarPop(List<Expression> arguments) {
        super("VAR_POP", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new VarPop(arguments);
    }

}
