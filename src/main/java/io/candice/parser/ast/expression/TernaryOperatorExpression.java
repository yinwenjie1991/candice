package io.candice.parser.ast.expression;

import java.util.Map;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-06
 */
public abstract class TernaryOperatorExpression extends AbstractExpression{
    private final Expression first;
    private final Expression second;
    private final Expression third;

    public TernaryOperatorExpression(Expression first, Expression second, Expression third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public Expression getFirst() {
        return first;
    }

    public Expression getSecond() {
        return second;
    }

    public Expression getThird() {
        return third;
    }

    @Override
    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return UNEVALUATABLE;
    }

}
