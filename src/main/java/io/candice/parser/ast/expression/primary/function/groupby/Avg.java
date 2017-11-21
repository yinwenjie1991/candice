/**
 * Baidu.com,Inc.
 * Copyright (c) 2000-2013 All Rights Reserved.
 */
package io.candice.parser.ast.expression.primary.function.groupby;

import com.baidu.hsb.parser.ast.expression.Expression;
import com.baidu.hsb.parser.ast.expression.primary.function.FunctionExpression;
import com.baidu.hsb.parser.visitor.SQLASTVisitor;

import java.util.List;

/**
 * @author xiongzhao@baidu.com
 */
public class Avg extends FunctionExpression {
    private final boolean distinct;

    public Avg(Expression expr, boolean distinct) {
        super("AVG", wrapList(expr));
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        throw new UnsupportedOperationException("function of char has special arguments");
    }

    @Override
    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
