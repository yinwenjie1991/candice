package io.candice.parser.ast.stmt;

import io.candice.parser.ast.ASTNode;

public interface SQLStatement extends ASTNode {
    public static enum StmtType {
        DML_SELECT,
        DML_DELETE,
        DML_INSERT,
        DML_REPLACE,
        DML_UPDATE,
        DML_CALL,
        DAL_SET,
        DAL_SHOW,
        MTL_START,
        /** COMMIT or ROLLBACK */
        MTL_TERMINATE,
        MTL_ISOLATION
    }
}
