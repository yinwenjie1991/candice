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
 * @version $Id: DateAdd.java, v 0.1 2013年12月30日 下午5:28:02 HI:brucest0078 Exp $
 */
public class DateAdd extends FunctionExpression {
    public DateAdd(List<Expression> arguments) {
        super("DATE_ADD", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new DateAdd(arguments);
    }

}
