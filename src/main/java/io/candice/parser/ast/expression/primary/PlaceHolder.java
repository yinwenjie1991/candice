/**
 * Baidu.com,Inc.
 * Copyright (c) 2000-2013 All Rights Reserved.
 */
package io.candice.parser.ast.expression.primary;

import com.baidu.hsb.parser.visitor.SQLASTVisitor;

import java.util.Map;

/**
 * @author xiongzhao@baidu.com
 */
public class PlaceHolder extends PrimaryExpression {
    private final String name;
    private final String nameUp;

    public PlaceHolder(String name, String nameUp) {
        this.name = name;
        this.nameUp = nameUp;
    }

    public String getName() {
        return name;
    }

    public String getNameUp() {
        return nameUp;
    }

    @Override
    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return parameters.get(nameUp);
    }

    @Override
    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

}
