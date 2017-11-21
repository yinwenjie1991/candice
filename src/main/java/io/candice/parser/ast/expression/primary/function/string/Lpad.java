package io.candice.parser.ast.expression.primary.function.string;


import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

/**
 * @author xiongzhao@baidu.com
 */
public class Lpad extends FunctionExpression {
    public Lpad(List<Expression> arguments) {
        super("LPAD", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Lpad(arguments);
    }

}
