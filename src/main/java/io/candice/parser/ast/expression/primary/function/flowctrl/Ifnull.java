package io.candice.parser.ast.expression.primary.function.flowctrl;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class Ifnull extends FunctionExpression {
    public Ifnull(List<Expression> arguments) {
        super("IFNULL", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Ifnull(arguments);
    }

}
