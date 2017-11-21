package io.candice.parser.ast.expression.primary.function.info;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class LastInsertId extends FunctionExpression {
    public LastInsertId(List<Expression> arguments) {
        super("LAST_INSERT_ID", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new LastInsertId(arguments);
    }

}
