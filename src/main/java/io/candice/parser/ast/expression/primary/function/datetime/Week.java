/**
 * Baidu.com,Inc.
 * Copyright (c) 2000-2013 All Rights Reserved.
 */
package io.candice.parser.ast.expression.primary.function.datetime;

import com.baidu.hsb.parser.ast.expression.Expression;
import com.baidu.hsb.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

/**
 * @author xiongzhao@baidu.com
 */
public class Week extends FunctionExpression {
    public Week(List<Expression> arguments) {
        super("WEEK", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Week(arguments);
    }

}
