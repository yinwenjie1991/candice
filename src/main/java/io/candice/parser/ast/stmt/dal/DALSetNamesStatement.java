package io.candice.parser.ast.stmt.dal;

import io.candice.parser.ast.stmt.SQLStatement;
import io.candice.parser.visitor.SQLASTVisitor;

public class DALSetNamesStatement implements SQLStatement {
    private final String charsetName;
    private final String collationName;

    public DALSetNamesStatement() {
        this.charsetName = null;
        this.collationName = null;
    }

    public DALSetNamesStatement(String charsetName, String collationName) {
        if (charsetName == null) throw new IllegalArgumentException("charsetName is null");
        this.charsetName = charsetName;
        this.collationName = collationName;
    }

    public boolean isDefault() {
        return charsetName == null;
    }

    public String getCharsetName() {
        return charsetName;
    }

    public String getCollationName() {
        return collationName;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
