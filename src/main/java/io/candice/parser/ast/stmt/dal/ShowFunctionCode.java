package io.candice.parser.ast.stmt.dal;

import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.visitor.SQLASTVisitor;

public class ShowFunctionCode extends DALShowStatement {
    private final Identifier functionName;

    public ShowFunctionCode(Identifier functionName) {
        this.functionName = functionName;
    }

    public Identifier getFunctionName() {
        return functionName;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
