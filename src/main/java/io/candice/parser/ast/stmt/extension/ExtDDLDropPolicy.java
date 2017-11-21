package io.candice.parser.ast.stmt.extension;

import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.ast.stmt.ddl.DDLStatement;
import io.candice.parser.visitor.SQLASTVisitor;

public class ExtDDLDropPolicy implements DDLStatement {
    private final Identifier policyName;

    public ExtDDLDropPolicy(Identifier policyName) {
        this.policyName = policyName;
    }

    public Identifier getPolicyName() {
        return policyName;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

}
