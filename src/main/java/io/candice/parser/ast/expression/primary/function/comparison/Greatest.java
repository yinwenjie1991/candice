/**
 * Baidu.com,Inc.
 * Copyright (c) 2000-2013 All Rights Reserved.
 */
package io.candice.parser.ast.expression.primary.function.comparison;

import com.baidu.hsb.parser.ast.expression.Expression;
import com.baidu.hsb.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

/**
 * 
 * 
 * @author xiongzhao@baidu.com
 * @version $Id: Greatest.java, v 0.1 2013年12月26日 下午8:10:42 HI:brucest0078 Exp $
 */
public class Greatest extends FunctionExpression {
    public Greatest(List<Expression> arguments) {
        super("GREATEST", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Greatest(arguments);
    }

}
