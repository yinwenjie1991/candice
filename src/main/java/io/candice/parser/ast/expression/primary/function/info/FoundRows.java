package io.candice.parser.ast.expression.primary.function.info;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class FoundRows extends FunctionExpression {
    public FoundRows(List<Expression> arguments) {
        super("FOUND_ROWS", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new FoundRows(arguments);
    }

}
