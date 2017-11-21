package io.candice.parser.ast.expression.primary.function.encryption;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class AesDecrypt extends FunctionExpression {
    public AesDecrypt(List<Expression> arguments) {
        super("AES_DECRYPT", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new AesDecrypt(arguments);
    }

}
