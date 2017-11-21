package io.candice.parser.ast.fragment.tableref;

import io.candice.parser.ast.ASTNode;

public interface TableReference extends ASTNode {
    int PRECEDENCE_REFS = 0;
    int PRECEDENCE_JOIN = 1;
    int PRECEDENCE_FACTOR = 2;

    /**
     * remove last condition element is success
     * 
     * @return {@link java.util.List List&lt;String&gt;} or
     *         {@link io.candice.parser.ast.expression.Expression
     *         Expression}. null if last condition element cannot be removed.
     */
    Object removeLastConditionElement();

    /**
     * @return true if and only if there is one table (not subquery) in table
     *         reference
     */
    public boolean isSingleTable();

    /**
     * @return precedences are defined in {@link TableReference}
     */
    int getPrecedence();
}
