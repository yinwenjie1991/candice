/**
 * Baidu.com,Inc.
 * Copyright (c) 2000-2013 All Rights Reserved.
 */
package io.candice.parser.ast.expression.primary.function.datetime;

import com.baidu.hsb.parser.ast.expression.Expression;
import com.baidu.hsb.parser.ast.expression.primary.function.FunctionExpression;
import com.baidu.hsb.parser.ast.expression.primary.literal.IntervalPrimary;
import com.baidu.hsb.parser.visitor.SQLASTVisitor;

import java.util.List;

/**
 * @author xiongzhao@baidu.com
 */
public class Timestampdiff extends FunctionExpression {
    private IntervalPrimary.Unit unit;

    public Timestampdiff(IntervalPrimary.Unit unit, List<Expression> arguments) {
        super("TIMESTAMPDIFF", arguments);
        this.unit = unit;
    }

    public IntervalPrimary.Unit getUnit() {
        return unit;
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        throw new UnsupportedOperationException("function of Timestampdiff has special arguments");
    }

    @Override
    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

}
