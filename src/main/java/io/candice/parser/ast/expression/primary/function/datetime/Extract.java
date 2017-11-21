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
public class Extract extends FunctionExpression {
    private IntervalPrimary.Unit unit;

    public Extract(IntervalPrimary.Unit unit, Expression date) {
        super("EXTRACT", wrapList(date));
        this.unit = unit;
    }

    public IntervalPrimary.Unit getUnit() {
        return unit;
    }

    public Expression getDate() {
        return arguments.get(0);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        throw new UnsupportedOperationException("function of extract has special arguments");
    }

    @Override
    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
