package io.candice.parser.ast.stmt.dml;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.misc.QueryExpression;

import java.util.Map;

public abstract class DMLQueryStatement extends DMLStatement implements QueryExpression {

    public int getPrecedence() {
        return PRECEDENCE_QUERY;
    }

    public Expression setCacheEvalRst(boolean cacheEvalRst) {
        return this;
    }

    public Object evaluation(Map<? extends Object, ? extends Object> parameters) {
        return UNEVALUATABLE;
    }
}
