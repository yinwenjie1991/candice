package io.candice.parser.ast.expression.primary.function.xml;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Extractvalue extends FunctionExpression {
    public Extractvalue(List<Expression> arguments) {
        super("EXTRACTVALUE", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Extractvalue(arguments);
    }

}
