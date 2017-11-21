package io.candice.parser.ast.expression.primary;

import io.candice.parser.ast.fragment.VariableScope;
import io.candice.parser.visitor.SQLASTVisitor;

public class SysVarPrimary extends VariableExpression {
    private final VariableScope scope;
    /** excluding starting "@@", '`' might be included */
    private final String varText;
    private final String varTextUp;

    public SysVarPrimary(VariableScope scope, String varText, String varTextUp) {
        this.scope = scope;
        this.varText = varText;
        this.varTextUp = varTextUp;
    }

    /**
     * @return never null
     */
    public VariableScope getScope() {
        return scope;
    }

    public String getVarTextUp() {
        return varTextUp;
    }

    public String getVarText() {
        return varText;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
