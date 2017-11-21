package io.candice.parser.ast.expression.primary.function.string;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class LoadFile extends FunctionExpression {
    public LoadFile(List<Expression> arguments) {
        super("LOAD_FILE", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new LoadFile(arguments);
    }

}
