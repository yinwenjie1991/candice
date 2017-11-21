package io.candice.parser.ast.fragment.tableref;

import io.candice.parser.visitor.SQLASTVisitor;

public class NaturalJoin implements TableReference {
    private final boolean isOuter;
    /**
     * make sense only if {@link #isOuter} is true. Eigher <code>LEFT</code> or
     * <code>RIGHT</code>
     */
    private final boolean isLeft;
    private final TableReference leftTableRef;
    private final TableReference rightTableRef;

    public NaturalJoin(boolean isOuter, boolean isLeft, TableReference leftTableRef, TableReference rightTableRef) {
        super();
        this.isOuter = isOuter;
        this.isLeft = isLeft;
        this.leftTableRef = leftTableRef;
        this.rightTableRef = rightTableRef;
    }

    public boolean isOuter() {
        return isOuter;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public TableReference getLeftTableRef() {
        return leftTableRef;
    }

    public TableReference getRightTableRef() {
        return rightTableRef;
    }

    public Object removeLastConditionElement() {
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
