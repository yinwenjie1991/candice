/**
 * Baidu.com,Inc.
 * Copyright (c) 2000-2013 All Rights Reserved.
 */
package io.candice.parser.ast.expression.primary.function.datetime;

import com.baidu.hsb.parser.ast.expression.Expression;
import com.baidu.hsb.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

/**
 * 
 * 
 * @author xiongzhao@baidu.com
 * @version $Id: Adddate.java, v 0.1 2013年12月26日 下午8:39:53 HI:brucest0078 Exp $
 */
public class Adddate extends FunctionExpression {
    public Adddate(List<Expression> arguments) {
        super("ADDDATE", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Adddate(arguments);
    }

}
