package io.candice.parser.ast.expression.primary.literal;

import io.candice.parser.visitor.SQLASTVisitor;

import java.util.Map;

public class LiteralNumber extends Literal {
    private final Number number;

    public LiteralNumber(Number number) {
        super();
        if (number == null) throw new IllegalArgumentException("number is null!");
        this.number = number;
    }

    @Override
    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return number;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

    public Number getNumber() {
        return number;
    }

}
