package io.candice.parser.ast.expression.primary.function.xml;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Updatexml extends FunctionExpression {
    public Updatexml(List<Expression> arguments) {
        super("UPDATEXML", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Updatexml(arguments);
    }

}
