package io.candice.parser.ast.expression.primary.function.info;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class RowCount extends FunctionExpression {
    public RowCount(List<Expression> arguments) {
        super("ROW_COUNT", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new RowCount(arguments);
    }
}
