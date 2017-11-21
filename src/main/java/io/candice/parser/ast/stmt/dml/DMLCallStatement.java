package io.candice.parser.ast.stmt.dml;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.visitor.SQLASTVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DMLCallStatement extends DMLStatement {
    private final Identifier procedure;
    private final List<Expression> arguments;

    public DMLCallStatement(Identifier procedure, List<Expression> arguments) {
        this.procedure = procedure;
        if (arguments == null || arguments.isEmpty()) {
            this.arguments = Collections.emptyList();
        } else if (arguments instanceof ArrayList) {
            this.arguments = arguments;
        } else {
            this.arguments = new ArrayList<Expression>(arguments);
        }
    }

    public DMLCallStatement(Identifier procedure) {
        this.procedure = procedure;
        this.arguments = Collections.emptyList();
    }

    public Identifier getProcedure() {
        return procedure;
    }

    /**
     * @return never null
     */
    public List<Expression> getArguments() {
        return arguments;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
