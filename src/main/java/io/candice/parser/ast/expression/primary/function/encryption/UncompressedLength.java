package io.candice.parser.ast.expression.primary.function.encryption;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class UncompressedLength extends FunctionExpression {
    public UncompressedLength(List<Expression> arguments) {
        super("UNCOMPRESSED_LENGTH", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new UncompressedLength(arguments);
    }

}
