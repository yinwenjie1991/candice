package io.candice.parser.ast.expression.primary;

import io.candice.parser.visitor.SQLASTVisitor;

public class UsrDefVarPrimary extends VariableExpression {
    /** include starting '@', e.g. "@'mary''s'" */
    private final String varText;

    public UsrDefVarPrimary(String varText) {
        this.varText = varText;
    }

    public String getVarText() {
        return varText;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
