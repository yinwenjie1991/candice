package io.candice.parser.ast.expression.bit;

import io.candice.parser.ast.expression.BinaryOperatorExpression;
import io.candice.parser.ast.expression.Expression;
import io.candice.parser.visitor.SQLASTVisitor;

/**
 * 
 * 
 * @author xiongzhao@baidu.com
 * @version $Id: BitAndExpression.java, v 0.1 2013年12月26日 下午6:09:23 HI:brucest0078 Exp $
 */
public class BitAndExpression extends BinaryOperatorExpression {
    public BitAndExpression(Expression leftOprand, Expression rightOprand) {
        super(leftOprand, rightOprand, PRECEDENCE_BIT_AND);
    }

    @Override
    public String getOperator() {
        return "&";
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
