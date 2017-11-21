package io.candice.parser.ast.expression.primary.function.string;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import io.candice.parser.visitor.SQLASTVisitor;

import java.util.List;

public class Substring extends FunctionExpression {
    public Substring(List<Expression> arguments) {
        super("SUBSTRING", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Substring(arguments);
    }

    @Override
    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
