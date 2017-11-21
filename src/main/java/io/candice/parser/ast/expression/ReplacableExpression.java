package io.candice.parser.ast.expression;

import io.candice.parser.ast.expression.primary.literal.LiteralBoolean;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-06
 */
public interface ReplacableExpression extends Expression {
    LiteralBoolean BOOL_FALSE = new LiteralBoolean(false);

    void setReplaceExpr(Expression replaceExpr);

    void clearReplaceExpr();
}
