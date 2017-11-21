package io.candice.parser.ast.expression.primary.function.string;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class SubstringIndex extends FunctionExpression {
    public SubstringIndex(List<Expression> arguments) {
        super("SUBSTRING_INDEX", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new SubstringIndex(arguments);
    }

}
