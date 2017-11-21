package io.candice.parser.ast.stmt.ddl;

import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.util.Pair;
import io.candice.parser.visitor.SQLASTVisitor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DDLRenameTableStatement implements DDLStatement {
    private final List<Pair<Identifier, Identifier>> list;

    public DDLRenameTableStatement() {
        this.list = new LinkedList<Pair<Identifier, Identifier>>();
    }

    public DDLRenameTableStatement(List<Pair<Identifier, Identifier>> list) {
        if (list == null) {
            this.list = Collections.emptyList();
        } else {
            this.list = list;
        }
    }

    public DDLRenameTableStatement addRenamePair(Identifier from, Identifier to) {
        list.add(new Pair<Identifier, Identifier>(from, to));
        return this;
    }

    public List<Pair<Identifier, Identifier>> getList() {
        return list;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

}
