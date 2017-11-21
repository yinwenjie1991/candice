package io.candice.parser.ast.stmt.dal;

import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.visitor.SQLASTVisitor;

public class ShowProcedureCode extends DALShowStatement {
    private final Identifier procedureName;

    public ShowProcedureCode(Identifier procedureName) {
        this.procedureName = procedureName;
    }

    public Identifier getProcedureName() {
        return procedureName;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
