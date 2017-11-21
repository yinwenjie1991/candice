package io.candice.parser.ast.stmt.dal;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.VariableExpression;
import io.candice.parser.ast.stmt.SQLStatement;
import io.candice.parser.util.Pair;
import io.candice.parser.visitor.SQLASTVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DALSetStatement implements SQLStatement {
    private final List<Pair<VariableExpression, Expression>> assignmentList;

    public DALSetStatement(List<Pair<VariableExpression, Expression>> assignmentList) {
        if (assignmentList == null || assignmentList.isEmpty()) {
            this.assignmentList = Collections.emptyList();
        } else if (assignmentList instanceof ArrayList) {
            this.assignmentList = assignmentList;
        } else {
            this.assignmentList = new ArrayList<Pair<VariableExpression, Expression>>(assignmentList);
        }
    }

    /**
     * @return never null
     */
    public List<Pair<VariableExpression, Expression>> getAssignmentList() {
        return assignmentList;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
