package io.candice.parser.ast.expression.primary.function.misc;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;


public class MasterPosWait extends FunctionExpression {
    public MasterPosWait(List<Expression> arguments) {
        super("MASTER_POS_WAIT", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new MasterPosWait(arguments);
    }

}
