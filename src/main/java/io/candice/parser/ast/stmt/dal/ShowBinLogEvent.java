package io.candice.parser.ast.stmt.dal;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.fragment.Limit;
import io.candice.parser.visitor.SQLASTVisitor;

public class ShowBinLogEvent extends DALShowStatement {
    private final String logName;
    private final Expression pos;
    private final Limit limit;

    public ShowBinLogEvent(String logName, Expression pos, Limit limit) {
        this.logName = logName;
        this.pos = pos;
        this.limit = limit;
    }

    public String getLogName() {
        return logName;
    }

    public Expression getPos() {
        return pos;
    }

    public Limit getLimit() {
        return limit;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
