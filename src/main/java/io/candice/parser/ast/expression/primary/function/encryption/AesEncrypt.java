package io.candice.parser.ast.expression.primary.function.encryption;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class AesEncrypt extends FunctionExpression {
    public AesEncrypt(List<Expression> arguments) {
        super("AES_ENCRYPT", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new AesEncrypt(arguments);
    }

}
