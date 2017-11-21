package io.candice.parser.ast.fragment.tableref;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.visitor.SQLASTVisitor;

public class StraightJoin implements TableReference {
    private final TableReference leftTableRef;
    private final TableReference rightTableRef;
    private Expression onCond;

    public StraightJoin(TableReference leftTableRef, TableReference rightTableRef, Expression onCond) {
        super();
        this.leftTableRef = leftTableRef;
        this.rightTableRef = rightTableRef;
        this.onCond = onCond;
    }

    public StraightJoin(TableReference leftTableRef, TableReference rightTableRef) {
        this(leftTableRef, rightTableRef, null);
    }

    public TableReference getLeftTableRef() {
        return leftTableRef;
    }

    public TableReference getRightTableRef() {
        return rightTableRef;
    }

    public Expression getOnCond() {
        return onCond;
    }

    public Object removeLastConditionElement() {
        if (onCond != null) {
            Object obj = onCond;
            onCond = null;
            return obj;
        }
        return null;
    }

    public boolean isSingleTable() {
        return false;
    }

    public int getPrecedence() {
        return TableReference.PRECEDENCE_JOIN;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

}
