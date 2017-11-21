package io.candice.parser.ast.stmt.extension;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.ast.stmt.ddl.DDLStatement;
import io.candice.parser.util.Pair;
import io.candice.parser.visitor.SQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

public class ExtDDLCreatePolicy implements DDLStatement {
    private final Identifier name;
    private final List<Pair<Integer, Expression>> proportion;

    public ExtDDLCreatePolicy(Identifier name) {
        this.name = name;
        this.proportion = new ArrayList<Pair<Integer, Expression>>(1);
    }

    public Identifier getName() {
        return name;
    }

    public List<Pair<Integer, Expression>> getProportion() {
        return proportion;
    }

    public ExtDDLCreatePolicy addProportion(Integer id, Expression val) {
        proportion.add(new Pair<Integer, Expression>(id, val));
        return this;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

}
