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
 * @version $Id: Tan.java, v 0.1 2013年12月26日 下午8:09:06 HI:brucest0078 Exp $
 */
public class Tan extends FunctionExpression {
    public Tan(List<Expression> arguments) {
        super("TAN", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Tan(arguments);
    }

}
