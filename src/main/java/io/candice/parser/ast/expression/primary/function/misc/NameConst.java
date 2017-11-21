package io.candice.parser.ast.expression.primary.function.misc;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class NameConst extends FunctionExpression {
    public NameConst(List<Expression> arguments) {
        super("NAME_CONST", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new NameConst(arguments);
    }

}
