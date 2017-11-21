package io.candice.parser.ast.expression;

import java.util.Map;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-06
 */
public abstract class AbstractExpression implements Expression{
    private boolean cacheEvalRst = true;
    private boolean evaluated;
    private Object evaluationCache;

    public Expression setCacheEvalRst(boolean cacheEvalRst) {
        this.cacheEvalRst = cacheEvalRst;
        return this;
    }

    public final Object evaluation(Map<? extends Object, ? extends Object> parameters) {
        if (cacheEvalRst) {
            if (evaluated) {
                return evaluationCache;
            }
            evaluationCache = evaluationInternal(parameters);
            evaluated = true;
            return evaluationCache;
        }
        return evaluationInternal(parameters);
    }

    protected abstract Object evaluationInternal(Map<? extends Object, ? extends Object> parameters);

}
