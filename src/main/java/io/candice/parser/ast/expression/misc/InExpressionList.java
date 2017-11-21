package io.candice.parser.ast.expression.misc;

import io.candice.parser.ast.expression.AbstractExpression;
import io.candice.parser.ast.expression.Expression;
import io.candice.parser.visitor.SQLASTVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class InExpressionList extends AbstractExpression {
    private List<Expression> list;

    public InExpressionList(List<Expression> list) {
        if (list == null || list.size() == 0) {
            this.list = Collections.emptyList();
        } else if (list instanceof ArrayList) {
            this.list = list;
        } else {
            this.list = new ArrayList<Expression>(list);
        }
    }

    /**
     * @return never null
     */
    public List<Expression> getList() {
        return list;
    }

    public int getPrecedence() {
        return PRECEDENCE_PRIMARY;
    }

    @Override
    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return UNEVALUATABLE;
    }

    private List<Expression> replaceList;

    public void setReplaceExpr(List<Expression> replaceList) {
        this.replaceList = replaceList;
    }

    public void clearReplaceExpr() {
        this.replaceList = null;
    }

    public void accept(SQLASTVisitor visitor) {
        if (replaceList == null) {
            visitor.visit(this);
        } else {
            List<Expression> temp = list;
            list = replaceList;
            visitor.visit(this);
            list = temp;
        }
    }
}
