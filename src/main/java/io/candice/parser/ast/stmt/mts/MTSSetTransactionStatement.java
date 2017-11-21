package io.candice.parser.ast.stmt.mts;

import io.candice.parser.ast.fragment.VariableScope;
import io.candice.parser.ast.stmt.SQLStatement;
import io.candice.parser.visitor.SQLASTVisitor;

public class MTSSetTransactionStatement implements SQLStatement {
    public static enum IsolationLevel {
        READ_UNCOMMITTED,
        READ_COMMITTED,
        REPEATABLE_READ,
        SERIALIZABLE
    }

    private final VariableScope scope;
    private final IsolationLevel level;

    public MTSSetTransactionStatement(VariableScope scope, IsolationLevel level) {
        super();
        if (level == null) throw new IllegalArgumentException("isolation level is null");
        this.level = level;
        this.scope = scope;
    }

    /**
     * @retern null means scope undefined
     */
    public VariableScope getScope() {
        return scope;
    }

    public IsolationLevel getLevel() {
        return level;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
