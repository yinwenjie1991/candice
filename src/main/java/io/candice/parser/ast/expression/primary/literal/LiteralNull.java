/**
 * Baidu.com,Inc.
 * Copyright (c) 2000-2013 All Rights Reserved.
 */
package io.candice.parser.ast.expression.primary.literal;

import com.baidu.hsb.parser.visitor.SQLASTVisitor;

import java.util.Map;

/**
 * @author xiongzhao@baidu.com
 */
public class LiteralNull extends Literal {
    @Override
    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return null;
    }

    @Override
    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
