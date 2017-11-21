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
 * @version $Id: Acos.java, v 0.1 2013年12月26日 下午8:04:58 HI:brucest0078 Exp $
 */
public class Acos extends FunctionExpression {
    public Acos(List<Expression> arguments) {
        super("ACOS", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Acos(arguments);
    }

}
