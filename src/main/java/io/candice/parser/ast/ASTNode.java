package io.candice.parser.ast;

import io.candice.parser.visitor.SQLASTVisitor;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-06
 */
public interface ASTNode {

    void accept(SQLASTVisitor visitor);

}
