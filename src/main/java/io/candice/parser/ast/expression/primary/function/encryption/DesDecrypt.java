package io.candice.parser.ast.expression.primary.function.encryption;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class DesDecrypt extends FunctionExpression {
    public DesDecrypt(List<Expression> arguments) {
        super("DES_DECRYPT", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new DesDecrypt(arguments);
    }

}
