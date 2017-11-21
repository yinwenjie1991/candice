package io.candice.parser.ast.expression.primary.function.encryption;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import java.util.List;

public class DesEncrypt extends FunctionExpression {
    public DesEncrypt(List<Expression> arguments) {
        super("DES_ENCRYPT", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new DesEncrypt(arguments);
    }

}
