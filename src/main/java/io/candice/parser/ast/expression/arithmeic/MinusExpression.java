package io.candice.parser.ast.expression.arithmeic;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.UnaryOperatorExpression;
import io.candice.parser.util.ExprEvalUtils;
import io.candice.parser.util.UnaryOperandCalculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

/**
 * 
 * 
 * @author xiongzhao@baidu.com
 * @version $Id: MinusExpression.java, v 0.1 2013年12月26日 下午6:09:00 HI:brucest0078 Exp $
 */
public class MinusExpression extends UnaryOperatorExpression implements UnaryOperandCalculator {
    public MinusExpression(Expression operand) {
        super(operand, PRECEDENCE_UNARY_OP);
    }

    @Override
    public String getOperator() {
        return "-";
    }

    @Override
    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        Object operand = getOperand().evaluation(parameters);
        if (operand == null)
            return null;
        if (operand == UNEVALUATABLE)
            return UNEVALUATABLE;
        Number num = null;
        if (operand instanceof String) {
            num = ExprEvalUtils.string2Number((String) operand);
        } else {
            num = (Number) operand;
        }
        return ExprEvalUtils.calculate(this, num);
    }

    public Number calculate(Integer num) {
        if (num == null)
            return null;
        int n = num.intValue();
        if (n == Integer.MIN_VALUE) {
            return new Long(-(long) n);
        }
        return new Integer(-n);
    }

    public Number calculate(Long num) {
        if (num == null)
            return null;
        long n = num.longValue();
        if (n == Long.MIN_VALUE) {
            return new Long(-(long) n);
        }
        return new Long(-n);
    }

    public Number calculate(BigInteger num) {
        if (num == null)
            return null;
        return num.negate();
    }

    public Number calculate(BigDecimal num) {
        if (num == null)
            return null;
        return num.negate();
    }
}
