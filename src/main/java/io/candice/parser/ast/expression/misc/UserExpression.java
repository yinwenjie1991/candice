package io.candice.parser.ast.expression.misc;

import io.candice.parser.ast.expression.primary.PrimaryExpression;
import io.candice.parser.visitor.SQLASTVisitor;

public class UserExpression extends PrimaryExpression {
    private final String userAtHost;

    /**
     * @param userAtHost
     */
    public UserExpression(String userAtHost) {
        super();
        this.userAtHost = userAtHost;
    }

    public String getUserAtHost() {
        return userAtHost;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
