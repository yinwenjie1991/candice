package io.candice.parser.ast.stmt.dml;

import io.candice.parser.ast.expression.misc.QueryExpression;
import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.ast.expression.primary.RowExpression;
import io.candice.parser.visitor.SQLASTVisitor;

import java.util.List;

public class DMLReplaceStatement extends DMLInsertReplaceStatement {
    public static enum ReplaceMode {
        /** default */
        UNDEF,
        LOW,
        DELAY
    }

    private final ReplaceMode mode;

    public DMLReplaceStatement(ReplaceMode mode, Identifier table, List<Identifier> columnNameList,
                               List<RowExpression> rowList) {
        super(table, columnNameList, rowList);
        this.mode = mode;
    }

    public DMLReplaceStatement(ReplaceMode mode, Identifier table, List<Identifier> columnNameList,
                               QueryExpression select) {
        super(table, columnNameList, select);
        this.mode = mode;
    }

    public ReplaceMode getMode() {
        return mode;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
