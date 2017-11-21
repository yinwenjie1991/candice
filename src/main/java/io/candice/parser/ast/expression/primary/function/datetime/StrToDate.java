package io.candice.parser.ast.expression.primary.function.datetime;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class StrToDate extends FunctionExpression {
    public StrToDate(List<Expression> arguments) {
        super("STR_TO_DATE", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new StrToDate(arguments);
    }

}
