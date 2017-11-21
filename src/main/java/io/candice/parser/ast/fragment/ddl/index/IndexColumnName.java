package io.candice.parser.ast.fragment.ddl.index;

import io.candice.parser.ast.ASTNode;
import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.visitor.SQLASTVisitor;

public class IndexColumnName implements ASTNode {
    private final Identifier columnName;
    /** null is possible */
    private final Expression length;
    private final boolean asc;

    public IndexColumnName(Identifier columnName, Expression length, boolean asc) {
        this.columnName = columnName;
        this.length = length;
        this.asc = asc;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

    public Identifier getColumnName() {
        return columnName;
    }

    public Expression getLength() {
        return length;
    }

    public boolean isAsc() {
        return asc;
    }

}
