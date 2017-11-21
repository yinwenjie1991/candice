package io.candice.parser.ast.expression.primary.literal;


import io.candice.parser.visitor.SQLASTVisitor;

import java.util.Map;

public class LiteralBoolean extends Literal {
    public static final Integer TRUE = new Integer(1);
    public static final Integer FALSE = new Integer(0);
    private final boolean value;

    public LiteralBoolean(boolean value) {
        super();
        this.value = value;
    }

    public boolean isTrue() {
        return value;
    }

    @Override
    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return value ? TRUE : FALSE;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
