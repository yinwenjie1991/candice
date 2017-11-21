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
 * @version $Id: DateFormat.java, v 0.1 2013年12月30日 下午5:28:17 HI:brucest0078 Exp $
 */
public class DateFormat extends FunctionExpression {
    public DateFormat(List<Expression> arguments) {
        super("DATE_FORMAT", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new DateFormat(arguments);
    }

}
