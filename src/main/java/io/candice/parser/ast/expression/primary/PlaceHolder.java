package io.candice.parser.ast.expression.primary;

import io.candice.parser.visitor.SQLASTVisitor;

import java.util.Map;

public class PlaceHolder extends PrimaryExpression {
    private final String name;
    private final String nameUp;

    public PlaceHolder(String name, String nameUp) {
        this.name = name;
        this.nameUp = nameUp;
    }

    public String getName() {
        return name;
    }

    public String getNameUp() {
        return nameUp;
    }

    @Override
    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return parameters.get(nameUp);
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

}
