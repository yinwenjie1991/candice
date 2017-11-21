/**
 * Baidu.com,Inc.
 * Copyright (c) 2000-2013 All Rights Reserved.
 */
package io.candice.parser.ast.expression.primary.function.string;

import com.baidu.hsb.parser.ast.expression.Expression;
import com.baidu.hsb.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

/**
 * @author xiongzhao@baidu.com
 */
public class Space extends FunctionExpression {
    public Space(List<Expression> arguments) {
        super("SPACE", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Space(arguments);
    }

}
