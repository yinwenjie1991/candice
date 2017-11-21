package io.candice.parser.ast.stmt.dal;

import io.candice.parser.visitor.SQLASTVisitor;

public class ShowPlugins extends DALShowStatement {

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
