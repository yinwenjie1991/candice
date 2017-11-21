package io.candice.parser.ast.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-06
 */
public abstract class PolyadicOperatorExpression extends AbstractExpression{
    protected List<Expression> operands;
    protected final int precedence;

    public PolyadicOperatorExpression(int precedence) {
        this(precedence, true);
    }

    public PolyadicOperatorExpression(int precedence, boolean leftCombine) {
        this(precedence, 4);
    }

    public PolyadicOperatorExpression(int precedence, int initArity) {
        this.precedence = precedence;
        this.operands = new ArrayList<Expression>(initArity);
    }

    /**
     * @return this
     */
    public PolyadicOperatorExpression appendOperand(Expression operand) {
        if (operand == null) return this;
        if (getClass().isAssignableFrom(operand.getClass())) {
            PolyadicOperatorExpression sub = (PolyadicOperatorExpression) operand;
            operands.addAll(sub.operands);
        } else {
            operands.add(operand);
        }
        return this;
    }

    /**
     * @param index start from 0
     */
    public Expression getOperand(int index) {
        if (index >= operands.size()) {
            throw new IllegalArgumentException("only contains "
                    + operands.size()
                    + " operands,"
                    + index
                    + " is out of bound");
        }
        return operands.get(index);
    }

    public int getArity() {
        return operands.size();
    }

    public int getPrecedence() {
        return precedence;
    }

    public abstract String getOperator();

    @Override
    protected Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return UNEVALUATABLE;
    }
}
