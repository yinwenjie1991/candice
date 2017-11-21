/**
 * Baidu.com,Inc.
 * Copyright (c) 2000-2013 All Rights Reserved.
 */
package io.candice.parser.visitor;

import com.baidu.hsb.parser.ast.expression.BinaryOperatorExpression;
import com.baidu.hsb.parser.ast.expression.PolyadicOperatorExpression;
import com.baidu.hsb.parser.ast.expression.UnaryOperatorExpression;
import com.baidu.hsb.parser.ast.expression.comparison.*;
import com.baidu.hsb.parser.ast.expression.logical.LogicalAndExpression;
import com.baidu.hsb.parser.ast.expression.logical.LogicalOrExpression;
import com.baidu.hsb.parser.ast.expression.misc.InExpressionList;
import com.baidu.hsb.parser.ast.expression.misc.UserExpression;
import com.baidu.hsb.parser.ast.expression.primary.*;
import com.baidu.hsb.parser.ast.expression.primary.function.FunctionExpression;
import com.baidu.hsb.parser.ast.expression.primary.function.cast.Cast;
import com.baidu.hsb.parser.ast.expression.primary.function.cast.Convert;
import com.baidu.hsb.parser.ast.expression.primary.function.datetime.Extract;
import com.baidu.hsb.parser.ast.expression.primary.function.datetime.GetFormat;
import com.baidu.hsb.parser.ast.expression.primary.function.datetime.Timestampadd;
import com.baidu.hsb.parser.ast.expression.primary.function.datetime.Timestampdiff;
import com.baidu.hsb.parser.ast.expression.primary.function.groupby.*;
import com.baidu.hsb.parser.ast.expression.primary.function.string.Char;
import com.baidu.hsb.parser.ast.expression.primary.function.string.Trim;
import com.baidu.hsb.parser.ast.expression.primary.literal.*;
import com.baidu.hsb.parser.ast.expression.string.LikeExpression;
import com.baidu.hsb.parser.ast.expression.type.CollateExpression;
import com.baidu.hsb.parser.ast.fragment.GroupBy;
import com.baidu.hsb.parser.ast.fragment.Limit;
import com.baidu.hsb.parser.ast.fragment.OrderBy;
import com.baidu.hsb.parser.ast.fragment.ddl.ColumnDefinition;
import com.baidu.hsb.parser.ast.fragment.ddl.TableOptions;
import com.baidu.hsb.parser.ast.fragment.ddl.datatype.DataType;
import com.baidu.hsb.parser.ast.fragment.ddl.index.IndexColumnName;
import com.baidu.hsb.parser.ast.fragment.ddl.index.IndexOption;
import com.baidu.hsb.parser.ast.fragment.tableref.*;
import com.baidu.hsb.parser.ast.stmt.dal.*;
import com.baidu.hsb.parser.ast.stmt.ddl.*;
import com.baidu.hsb.parser.ast.stmt.ddl.DDLAlterTableStatement.AlterSpecification;
import com.baidu.hsb.parser.ast.stmt.dml.*;
import com.baidu.hsb.parser.ast.stmt.extension.ExtDDLCreatePolicy;
import com.baidu.hsb.parser.ast.stmt.extension.ExtDDLDropPolicy;
import com.baidu.hsb.parser.ast.stmt.mts.MTSReleaseStatement;
import com.baidu.hsb.parser.ast.stmt.mts.MTSRollbackStatement;
import com.baidu.hsb.parser.ast.stmt.mts.MTSSavepointStatement;
import com.baidu.hsb.parser.ast.stmt.mts.MTSSetTransactionStatement;

/**
 * @author xiongzhao@baidu.com
 */
public interface SQLASTVisitor {

    void visit(BetweenAndExpression node);

    void visit(ComparisionIsExpression node);

    void visit(InExpressionList node);

    void visit(LikeExpression node);

    void visit(CollateExpression node);

    void visit(UserExpression node);

    void visit(UnaryOperatorExpression node);

    void visit(BinaryOperatorExpression node);

    void visit(PolyadicOperatorExpression node);

    void visit(LogicalAndExpression node);

    void visit(LogicalOrExpression node);

    void visit(ComparisionEqualsExpression node);

    void visit(ComparisionNullSafeEqualsExpression node);

    void visit(InExpression node);

    //-------------------------------------------------------
    void visit(FunctionExpression node);

    void visit(Char node);

    void visit(Convert node);

    void visit(Trim node);

    void visit(Cast node);

    void visit(Avg node);

    void visit(Max node);

    void visit(Min node);

    void visit(Sum node);

    void visit(Count node);

    void visit(GroupConcat node);

    void visit(Extract node);

    void visit(Timestampdiff node);

    void visit(Timestampadd node);

    void visit(GetFormat node);

    //-------------------------------------------------------
    void visit(IntervalPrimary node);

    void visit(LiteralBitField node);

    void visit(LiteralBoolean node);

    void visit(LiteralHexadecimal node);

    void visit(LiteralNull node);

    void visit(LiteralNumber node);

    void visit(LiteralString node);

    void visit(CaseWhenOperatorExpression node);

    void visit(DefaultValue node);

    void visit(ExistsPrimary node);

    void visit(PlaceHolder node);

    void visit(Identifier node);

    void visit(MatchExpression node);

    void visit(ParamMarker node);

    void visit(RowExpression node);

    void visit(SysVarPrimary node);

    void visit(UsrDefVarPrimary node);

    //-------------------------------------------------------
    void visit(IndexHint node);

    void visit(InnerJoin node);

    void visit(NaturalJoin node);

    void visit(OuterJoin node);

    void visit(StraightJoin node);

    void visit(SubqueryFactor node);

    void visit(TableReferences node);

    void visit(TableRefFactor node);

    void visit(Dual dual);

    void visit(GroupBy node);

    void visit(Limit node);

    void visit(OrderBy node);

    void visit(ColumnDefinition node);

    void visit(IndexOption node);

    void visit(IndexColumnName node);

    void visit(TableOptions node);

    void visit(AlterSpecification node);

    void visit(DataType node);

    //-------------------------------------------------------
    void visit(ShowAuthors node);

    void visit(ShowBinaryLog node);

    void visit(ShowBinLogEvent node);

    void visit(ShowCharaterSet node);

    void visit(ShowCollation node);

    void visit(ShowColumns node);

    void visit(ShowContributors node);

    void visit(ShowCreate node);

    void visit(ShowDatabases node);

    void visit(ShowEngine node);

    void visit(ShowEngines node);

    void visit(ShowErrors node);

    void visit(ShowEvents node);

    void visit(ShowFunctionCode node);

    void visit(ShowFunctionStatus node);

    void visit(ShowGrants node);

    void visit(ShowIndex node);

    void visit(ShowMasterStatus node);

    void visit(ShowOpenTables node);

    void visit(ShowPlugins node);

    void visit(ShowPrivileges node);

    void visit(ShowProcedureCode node);

    void visit(ShowProcedureStatus node);

    void visit(ShowProcesslist node);

    void visit(ShowProfile node);

    void visit(ShowProfiles node);

    void visit(ShowSlaveHosts node);

    void visit(ShowSlaveStatus node);

    void visit(ShowStatus node);

    void visit(ShowTables node);

    void visit(ShowTableStatus node);

    void visit(ShowTriggers node);

    void visit(ShowVariables node);

    void visit(ShowWarnings node);

    void visit(DescTableStatement node);

    void visit(DALSetStatement node);

    void visit(DALSetNamesStatement node);

    void visit(DALSetCharacterSetStatement node);

    //-------------------------------------------------------
    void visit(DMLCallStatement node);

    void visit(DMLDeleteStatement node);

    void visit(DMLInsertStatement node);

    void visit(DMLReplaceStatement node);

    void visit(DMLSelectStatement node);

    void visit(DMLSelectUnionStatement node);

    void visit(DMLUpdateStatement node);

    void visit(MTSSetTransactionStatement node);

    void visit(MTSSavepointStatement node);

    void visit(MTSReleaseStatement node);

    void visit(MTSRollbackStatement node);

    void visit(DDLTruncateStatement node);

    void visit(DDLAlterTableStatement node);

    void visit(DDLCreateIndexStatement node);

    void visit(DDLCreateTableStatement node);

    void visit(DDLRenameTableStatement node);

    void visit(DDLDropIndexStatement node);

    void visit(DDLDropTableStatement node);

    void visit(ExtDDLCreatePolicy node);

    void visit(ExtDDLDropPolicy node);

}
