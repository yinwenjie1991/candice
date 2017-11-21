package io.candice.parser.ast.fragment.tableref;

import io.candice.parser.visitor.SQLASTVisitor;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

public class TableReferences implements TableReference {
    protected static List<TableReference> ensureListType(List<TableReference> list) {
        if (list instanceof ArrayList) return list;
        return new ArrayList<TableReference>(list);
    }

    private final List<TableReference> list;

    /**
     * @return never null
     */
    public List<TableReference> getTableReferenceList() {
        return list;
    }

    public TableReferences(List<TableReference> list) throws SQLSyntaxErrorException {
        if (list == null || list.isEmpty()) {
            throw new SQLSyntaxErrorException("at least one table reference");
        }
        this.list = ensureListType(list);
    }

    @Override
    public Object removeLastConditionElement() {
        if (list != null && !list.isEmpty()) {
            return list.get(list.size() - 1).removeLastConditionElement();
        }
        return null;
    }

    public boolean isSingleTable() {
        if (list == null) {
            return false;
        }
        int count = 0;
        TableReference first = null;
        for (TableReference ref : list) {
            if (ref != null && 1 == ++count) {
                first = ref;
            }
        }
        return count == 1 && first.isSingleTable();
    }

    public int getPrecedence() {
        return TableReference.PRECEDENCE_REFS;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

}
