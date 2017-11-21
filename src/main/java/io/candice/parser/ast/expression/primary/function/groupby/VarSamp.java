package io.candice.parser.ast.expression.primary.function.groupby;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class VarSamp extends FunctionExpression {
    public VarSamp(List<Expression> arguments) {
        super("VAR_SAMP", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new VarSamp(arguments);
    }

}
