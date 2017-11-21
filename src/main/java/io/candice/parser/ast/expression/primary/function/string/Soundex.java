package io.candice.parser.ast.expression.primary.function.string;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Soundex extends FunctionExpression {
    public Soundex(List<Expression> arguments) {
        super("SOUNDEX", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Soundex(arguments);
    }

}
