/**
 * Baidu.com,Inc.
 * Copyright (c) 2000-2013 All Rights Reserved.
 */
package io.candice.parser.ast.expression.primary.function.arithmetic;

import com.baidu.hsb.parser.ast.expression.Expression;
import com.baidu.hsb.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

/**
 * @author xiongzhao@baidu.com
 * @version $Id: Abs.java, v 0.1 2013年12月26日 下午8:04:40 HI:brucest0078 Exp $
 */
public class Abs extends FunctionExpression {
    public Abs(List<Expression> arguments) {
        super("ABS", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Abs(arguments);
    }

}
