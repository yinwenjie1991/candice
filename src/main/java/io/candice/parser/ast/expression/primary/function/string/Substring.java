/**
 * Baidu.com,Inc.
 * Copyright (c) 2000-2013 All Rights Reserved.
 */
package io.candice.parser.ast.expression.primary.function.string;

import com.baidu.hsb.parser.ast.expression.Expression;
import com.baidu.hsb.parser.ast.expression.primary.function.FunctionExpression;
import com.baidu.hsb.parser.visitor.SQLASTVisitor;

import java.util.List;

/**
 * @author xiongzhao@baidu.com
 */
public class Substring extends FunctionExpression {
    public Substring(List<Expression> arguments) {
        super("SUBSTRING", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Substring(arguments);
    }

    @Override
    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
