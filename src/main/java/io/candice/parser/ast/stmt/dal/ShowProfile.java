package io.candice.parser.ast.stmt.dal;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.fragment.Limit;
import io.candice.parser.visitor.SQLASTVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowProfile extends DALShowStatement {
    /** enum name must equals to real sql while ' ' is replaced with '_' */
    public static enum Type {
        ALL,
        BLOCK_IO,
        CONTEXT_SWITCHES,
        CPU,
        IPC,
        MEMORY,
        PAGE_FAULTS,
        SOURCE,
        SWAPS
    }

    private final List<Type> types;
    private final Expression forQuery;
    private final Limit limit;

    public ShowProfile(List<Type> types, Expression forQuery, Limit limit) {
        if (types == null || types.isEmpty()) {
            this.types = Collections.emptyList();
        } else if (types instanceof ArrayList) {
            this.types = types;
        } else {
            this.types = new ArrayList<Type>(types);
        }
        this.forQuery = forQuery;
        this.limit = limit;
    }

    /**
     * @return never null
     */
    public List<Type> getTypes() {
        return types;
    }

    public Expression getForQuery() {
        return forQuery;
    }

    public Limit getLimit() {
        return limit;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
