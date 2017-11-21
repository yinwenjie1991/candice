package io.candice.parser.ast.expression.primary.function.string;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class ExportSet extends FunctionExpression {
    public ExportSet(List<Expression> arguments) {
        super("EXPORT_SET", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new ExportSet(arguments);
    }

}
