package io.candice.parser.ast.stmt.dml;

import io.candice.parser.ast.fragment.Limit;
import io.candice.parser.ast.fragment.OrderBy;
import io.candice.parser.visitor.SQLASTVisitor;

import java.util.LinkedList;
import java.util.List;

public class DMLSelectUnionStatement extends DMLQueryStatement {
    /** might be {@link LinkedList} */
    private final List<DMLSelectStatement> selectStmtList;
    /**
     * <code>Mixed UNION types are treated such that a DISTINCT union overrides any ALL union to its left</code>
     * <br/>
     * 0 means all relations of selects are union all<br/>
     * last index of {@link #selectStmtList} means all relations of selects are
     * union distinct<br/>
     */
    private int firstDistinctIndex = 0;
    private OrderBy orderBy;
    private Limit limit;

    public DMLSelectUnionStatement(DMLSelectStatement select) {
        super();
        this.selectStmtList = new LinkedList<DMLSelectStatement>();
        this.selectStmtList.add(select);
    }

    public DMLSelectUnionStatement addSelect(DMLSelectStatement select, boolean unionAll) {
        selectStmtList.add(select);
        if (!unionAll) {
            firstDistinctIndex = selectStmtList.size() - 1;
        }
        return this;
    }

    public DMLSelectUnionStatement setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public DMLSelectUnionStatement setLimit(Limit limit) {
        this.limit = limit;
        return this;
    }

    public List<DMLSelectStatement> getSelectStmtList() {
        return selectStmtList;
    }

    public int getFirstDistinctIndex() {
        return firstDistinctIndex;
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
