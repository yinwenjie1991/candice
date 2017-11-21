package io.candice.parser.ast.expression.primary.function.info;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Benchmark extends FunctionExpression {
    public Benchmark(List<Expression> arguments) {
        super("BENCHMARK", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Benchmark(arguments);
    }

}
