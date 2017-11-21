/**
 * Baidu.com,Inc.
 * Copyright (c) 2000-2013 All Rights Reserved.
 */
package io.candice.parser.ast.stmt.dml;

import com.baidu.hsb.parser.ast.expression.Expression;
import com.baidu.hsb.parser.ast.expression.misc.QueryExpression;

import java.util.Map;

/**
 * @author xiongzhao@baidu.com
 */
public abstract class DMLQueryStatement extends DMLStatement implements QueryExpression {
    @Override
    public int getPrecedence() {
        return PRECEDENCE_QUERY;
    }

    @Override
    public Expression setCacheEvalRst(boolean cacheEvalRst) {
        return this;
    }

    @Override
    public Object evaluation(Map<? extends Object, ? extends Object> parameters) {
        return UNEVALUATABLE;
    }
}
