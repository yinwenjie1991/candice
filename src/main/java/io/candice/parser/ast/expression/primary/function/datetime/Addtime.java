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
 * @version $Id: Addtime.java, v 0.1 2013年12月30日 下午5:27:19 HI:brucest0078 Exp $
 */
public class Addtime extends FunctionExpression {
    public Addtime(List<Expression> arguments) {
        super("ADDTIME", arguments);
    }

    @Override
    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Addtime(arguments);
    }

}
