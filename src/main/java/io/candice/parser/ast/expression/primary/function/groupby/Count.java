package io.candice.parser.ast.expression.primary.function.groupby;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import io.candice.parser.visitor.SQLASTVisitor;

import java.util.List;

public class Count extends FunctionExpression {

    /**
     * either {@link distinct} or {@link wildcard} is false. if both are false,
     * expressionList must be size 1
     */
    private final boolean distinct;

    public Count(List<Expression> arguments) {
        super("COUNT", arguments);
        this.distinct = true;
    }

    public Count(Expression arg) {
        super("COUNT", wrapList(arg));
        this.distinct = false;
    }

    public boolean isDistinct() {
        return distinct;
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Count(arguments);
    }

    @Override
    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
