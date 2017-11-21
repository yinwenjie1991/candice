package io.candice.parser.ast.stmt.dal;

import io.candice.parser.ast.stmt.SQLStatement;
import io.candice.parser.visitor.SQLASTVisitor;

public class DALSetCharacterSetStatement implements SQLStatement {
    private final String charset;

    public DALSetCharacterSetStatement() {
        this.charset = null;
    }

    public DALSetCharacterSetStatement(String charset) {
        if (charset == null) throw new IllegalArgumentException("charsetName is null");
        this.charset = charset;
    }

    public boolean isDefault() {
        return charset == null;
    }

    public String getCharset() {
        return charset;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

}
