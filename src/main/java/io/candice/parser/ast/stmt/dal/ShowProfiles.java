/**
 * Baidu.com,Inc.
 * Copyright (c) 2000-2013 All Rights Reserved.
 */
package io.candice.parser.ast.stmt.dal;

import com.baidu.hsb.parser.visitor.SQLASTVisitor;

/**
 * @author xiongzhao@baidu.com
 */
public class ShowProfiles extends DALShowStatement {

    @Override
    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
