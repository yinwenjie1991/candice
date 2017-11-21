package io.candice.parser.ast.expression;

import io.candice.parser.visitor.SQLASTVisitor;

import java.util.Map;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-06
 */
public abstract class UnaryOperatorExpression extends AbstractExpression{
    private final Expression operand;
    protected final int precedence;

    public UnaryOperatorExpression(Expression operand, int precedence) {
        if (operand == null) throw new IllegalArgumentException("operand is null");
        this.operand = operand;
        this.precedence = precedence;
    }

    public Expression getOperand() {
        return operand;
    }

    public int getPrecedence() {
        return precedence;
    }

    public abstract String getOperator();

    @Override
    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return UNEVALUATABLE;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

}
