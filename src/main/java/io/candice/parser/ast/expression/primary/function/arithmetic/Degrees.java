/**
 * Baidu.com,Inc.
 * Copyright (c) 2000-2013 All Rights Reserved.
 */
package io.candice.parser.ast.expression.primary.function.arithmetic;

import com.baidu.hsb.parser.ast.expression.Expression;
import com.baidu.hsb.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

/**
 * 
 * 
 * @author xiongzhao@baidu.com
 * @version $Id: Degrees.java, v 0.1 2013年12月26日 下午8:06:34 HI:brucest0078 Exp $
 */
public class Degrees extends FunctionExpression {
    public Degrees(List<Expression> arguments) {
        super("DEGREES", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Degrees(arguments);
    }

}
