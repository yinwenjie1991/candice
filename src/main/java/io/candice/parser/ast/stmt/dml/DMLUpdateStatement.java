package io.candice.parser.ast.stmt.dml;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.ast.fragment.Limit;
import io.candice.parser.ast.fragment.OrderBy;
import io.candice.parser.ast.fragment.tableref.TableReferences;
import io.candice.parser.util.Pair;
import io.candice.parser.visitor.SQLASTVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DMLUpdateStatement extends DMLStatement {
    private final boolean lowPriority;
    private final boolean ignore;
    private final TableReferences tableRefs;
    private final List<Pair<Identifier, Expression>> values;
    private final Expression where;
    private final OrderBy orderBy;
    private final Limit limit;

    public DMLUpdateStatement(boolean lowPriority, boolean ignore, TableReferences tableRefs,
                              List<Pair<Identifier, Expression>> values, Expression where, OrderBy orderBy, Limit limit) {
        this.lowPriority = lowPriority;
        this.ignore = ignore;
        if (tableRefs == null) throw new IllegalArgumentException("argument tableRefs is null for update stmt");
        this.tableRefs = tableRefs;
        if (values == null || values.size() <= 0) {
            this.values = Collections.emptyList();
        } else if (!(values instanceof ArrayList)) {
            this.values = new ArrayList<Pair<Identifier, Expression>>(values);
        } else {
            this.values = values;
        }
        this.where = where;
        this.orderBy = orderBy;
        this.limit = limit;
    }

    public boolean isLowPriority() {
        return lowPriority;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public TableReferences getTableRefs() {
        return tableRefs;
    }

    public List<Pair<Identifier, Expression>> getValues() {
        return values;
    }

    public Expression getWhere() {
        return where;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public Limit getLimit() {
        return limit;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
