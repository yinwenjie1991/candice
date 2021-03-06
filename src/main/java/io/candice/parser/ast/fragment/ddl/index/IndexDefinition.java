package io.candice.parser.ast.fragment.ddl.index;

import io.candice.parser.ast.ASTNode;
import io.candice.parser.visitor.SQLASTVisitor;

import java.util.Collections;
import java.util.List;

public class IndexDefinition implements ASTNode {
    public static enum IndexType {
        BTREE,
        HASH
    }

    private final IndexType indexType;
    private final List<IndexColumnName> columns;
    private final List<IndexOption> options;

    @SuppressWarnings("unchecked")
    public IndexDefinition(IndexType indexType, List<IndexColumnName> columns, List<IndexOption> options) {
        this.indexType = indexType;
        if (columns == null || columns.isEmpty()) throw new IllegalArgumentException("columns is null or empty");
        this.columns = columns;
        this.options = (List<IndexOption>) (options == null || options.isEmpty() ? Collections.emptyList() : options);
    }

    public IndexType getIndexType() {
        return indexType;
    }

    /**
     * @return never null
     */
    public List<IndexColumnName> getColumns() {
        return columns;
    }

    /**
     * @return never null
     */
    public List<IndexOption> getOptions() {
        return options;
    }

    public void accept(SQLASTVisitor visitor) {
        // QS_TODO

    }

}
