package io.candice.route.visitor;

import io.candice.parser.ast.expression.BinaryOperatorExpression;
import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.PolyadicOperatorExpression;
import io.candice.parser.ast.expression.UnaryOperatorExpression;
import io.candice.parser.ast.expression.comparison.*;
import io.candice.parser.ast.expression.logical.LogicalAndExpression;
import io.candice.parser.ast.expression.logical.LogicalOrExpression;
import io.candice.parser.ast.expression.misc.InExpressionList;
import io.candice.parser.ast.expression.misc.UserExpression;
import io.candice.parser.ast.expression.primary.*;
import io.candice.parser.ast.expression.primary.function.FunctionExpression;
import io.candice.parser.ast.expression.primary.function.cast.Cast;
import io.candice.parser.ast.expression.primary.function.cast.Convert;
import io.candice.parser.ast.expression.primary.function.datetime.Extract;
import io.candice.parser.ast.expression.primary.function.datetime.GetFormat;
import io.candice.parser.ast.expression.primary.function.datetime.Timestampadd;
import io.candice.parser.ast.expression.primary.function.datetime.Timestampdiff;
import io.candice.parser.ast.expression.primary.function.groupby.*;
import io.candice.parser.ast.expression.primary.function.string.Char;
import io.candice.parser.ast.expression.primary.function.string.Trim;
import io.candice.parser.ast.expression.primary.literal.*;
import io.candice.parser.ast.expression.string.LikeExpression;
import io.candice.parser.ast.expression.type.CollateExpression;
import io.candice.parser.ast.fragment.GroupBy;
import io.candice.parser.ast.fragment.Limit;
import io.candice.parser.ast.fragment.OrderBy;
import io.candice.parser.ast.fragment.ddl.ColumnDefinition;
import io.candice.parser.ast.fragment.ddl.TableOptions;
import io.candice.parser.ast.fragment.ddl.datatype.DataType;
import io.candice.parser.ast.fragment.ddl.index.IndexColumnName;
import io.candice.parser.ast.fragment.ddl.index.IndexOption;
import io.candice.parser.ast.fragment.tableref.*;
import io.candice.parser.ast.stmt.dal.*;
import io.candice.parser.ast.stmt.ddl.*;
import io.candice.parser.ast.stmt.ddl.DDLAlterTableStatement.AlterSpecification;
import io.candice.parser.ast.stmt.dml.*;
import io.candice.parser.ast.stmt.extension.ExtDDLCreatePolicy;
import io.candice.parser.ast.stmt.extension.ExtDDLDropPolicy;
import io.candice.parser.ast.stmt.mts.MTSReleaseStatement;
import io.candice.parser.ast.stmt.mts.MTSRollbackStatement;
import io.candice.parser.ast.stmt.mts.MTSSavepointStatement;
import io.candice.parser.ast.stmt.mts.MTSSetTransactionStatement;
import io.candice.parser.visitor.SQLASTVisitor;

import java.util.HashSet;
import java.util.Set;

public class HPartitionVisitor implements SQLASTVisitor {

    private static final Set<Class<? extends Expression>> VERDICT_PASS_THROUGH_WHERE     = new HashSet<Class<? extends Expression>>(
                                                                                             6);
    private static final Set<Class<? extends Expression>> GROUP_FUNC_PASS_THROUGH_SELECT = new HashSet<Class<? extends Expression>>(
                                                                                             5);
    private static final Set<Class<? extends Expression>> PARTITION_OPERAND_SINGLE       = new HashSet<Class<? extends Expression>>(
                                                                                             3);
    static {
        VERDICT_PASS_THROUGH_WHERE.add(LogicalAndExpression.class);
        VERDICT_PASS_THROUGH_WHERE.add(LogicalOrExpression.class);
        VERDICT_PASS_THROUGH_WHERE.add(BetweenAndExpression.class);
        VERDICT_PASS_THROUGH_WHERE.add(InExpression.class);
        VERDICT_PASS_THROUGH_WHERE.add(ComparisionNullSafeEqualsExpression.class);
        VERDICT_PASS_THROUGH_WHERE.add(ComparisionEqualsExpression.class);
        GROUP_FUNC_PASS_THROUGH_SELECT.add(Count.class);
        GROUP_FUNC_PASS_THROUGH_SELECT.add(Sum.class);
        GROUP_FUNC_PASS_THROUGH_SELECT.add(Min.class);
        GROUP_FUNC_PASS_THROUGH_SELECT.add(Max.class);
        GROUP_FUNC_PASS_THROUGH_SELECT.add(Wildcard.class);
        PARTITION_OPERAND_SINGLE.add(BetweenAndExpression.class);
        PARTITION_OPERAND_SINGLE.add(ComparisionNullSafeEqualsExpression.class);
        PARTITION_OPERAND_SINGLE.add(ComparisionEqualsExpression.class);
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.comparison.BetweenAndExpression)
     */
    @Override
    public void visit(BetweenAndExpression node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.comparison.ComparisionIsExpression)
     */
    @Override
    public void visit(ComparisionIsExpression node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.misc.InExpressionList)
     */
    @Override
    public void visit(InExpressionList node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.string.LikeExpression)
     */
    @Override
    public void visit(LikeExpression node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.type.CollateExpression)
     */
    @Override
    public void visit(CollateExpression node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.misc.UserExpression)
     */
    @Override
    public void visit(UserExpression node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.UnaryOperatorExpression)
     */
    @Override
    public void visit(UnaryOperatorExpression node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.BinaryOperatorExpression)
     */
    @Override
    public void visit(BinaryOperatorExpression node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.PolyadicOperatorExpression)
     */
    @Override
    public void visit(PolyadicOperatorExpression node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.logical.LogicalAndExpression)
     */
    @Override
    public void visit(LogicalAndExpression node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.logical.LogicalOrExpression)
     */
    @Override
    public void visit(LogicalOrExpression node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.comparison.ComparisionEqualsExpression)
     */
    @Override
    public void visit(ComparisionEqualsExpression node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.comparison.ComparisionNullSafeEqualsExpression)
     */
    @Override
    public void visit(ComparisionNullSafeEqualsExpression node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.comparison.InExpression)
     */
    @Override
    public void visit(InExpression node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.function.FunctionExpression)
     */
    @Override
    public void visit(FunctionExpression node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.function.string.Char)
     */
    @Override
    public void visit(Char node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.function.cast.Convert)
     */
    @Override
    public void visit(Convert node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.function.string.Trim)
     */
    @Override
    public void visit(Trim node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.function.cast.Cast)
     */
    @Override
    public void visit(Cast node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.function.groupby.Avg)
     */
    @Override
    public void visit(Avg node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.function.groupby.Max)
     */
    @Override
    public void visit(Max node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.function.groupby.Min)
     */
    @Override
    public void visit(Min node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.function.groupby.Sum)
     */
    @Override
    public void visit(Sum node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.function.groupby.Count)
     */
    @Override
    public void visit(Count node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.function.groupby.GroupConcat)
     */
    @Override
    public void visit(GroupConcat node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.function.datetime.Extract)
     */
    @Override
    public void visit(Extract node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.function.datetime.Timestampdiff)
     */
    @Override
    public void visit(Timestampdiff node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.function.datetime.Timestampadd)
     */
    @Override
    public void visit(Timestampadd node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.function.datetime.GetFormat)
     */
    @Override
    public void visit(GetFormat node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.literal.IntervalPrimary)
     */
    @Override
    public void visit(IntervalPrimary node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.literal.LiteralBitField)
     */
    @Override
    public void visit(LiteralBitField node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.literal.LiteralBoolean)
     */
    @Override
    public void visit(LiteralBoolean node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.literal.LiteralHexadecimal)
     */
    @Override
    public void visit(LiteralHexadecimal node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.literal.LiteralNull)
     */
    @Override
    public void visit(LiteralNull node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.literal.LiteralNumber)
     */
    @Override
    public void visit(LiteralNumber node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.literal.LiteralString)
     */
    @Override
    public void visit(LiteralString node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.CaseWhenOperatorExpression)
     */
    @Override
    public void visit(CaseWhenOperatorExpression node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.DefaultValue)
     */
    @Override
    public void visit(DefaultValue node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.ExistsPrimary)
     */
    @Override
    public void visit(ExistsPrimary node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.PlaceHolder)
     */
    @Override
    public void visit(PlaceHolder node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.Identifier)
     */
    @Override
    public void visit(Identifier node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.MatchExpression)
     */
    @Override
    public void visit(MatchExpression node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.ParamMarker)
     */
    @Override
    public void visit(ParamMarker node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.RowExpression)
     */
    @Override
    public void visit(RowExpression node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.SysVarPrimary)
     */
    @Override
    public void visit(SysVarPrimary node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.expression.primary.UsrDefVarPrimary)
     */
    @Override
    public void visit(UsrDefVarPrimary node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.fragment.tableref.IndexHint)
     */
    @Override
    public void visit(IndexHint node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.fragment.tableref.InnerJoin)
     */
    @Override
    public void visit(InnerJoin node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.fragment.tableref.NaturalJoin)
     */
    @Override
    public void visit(NaturalJoin node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.fragment.tableref.OuterJoin)
     */
    @Override
    public void visit(OuterJoin node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.fragment.tableref.StraightJoin)
     */
    @Override
    public void visit(StraightJoin node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.fragment.tableref.SubqueryFactor)
     */
    @Override
    public void visit(SubqueryFactor node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.fragment.tableref.TableReferences)
     */
    @Override
    public void visit(TableReferences node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.fragment.tableref.TableRefFactor)
     */
    @Override
    public void visit(TableRefFactor node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.fragment.tableref.Dual)
     */
    @Override
    public void visit(Dual dual) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.fragment.GroupBy)
     */
    @Override
    public void visit(GroupBy node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.fragment.Limit)
     */
    @Override
    public void visit(Limit node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.fragment.OrderBy)
     */
    @Override
    public void visit(OrderBy node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.fragment.ddl.ColumnDefinition)
     */
    @Override
    public void visit(ColumnDefinition node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.fragment.ddl.index.IndexOption)
     */
    @Override
    public void visit(IndexOption node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.fragment.ddl.index.IndexColumnName)
     */
    @Override
    public void visit(IndexColumnName node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.fragment.ddl.TableOptions)
     */
    @Override
    public void visit(TableOptions node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.ddl.DDLAlterTableStatement.AlterSpecification)
     */
    @Override
    public void visit(AlterSpecification node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.fragment.ddl.datatype.DataType)
     */
    @Override
    public void visit(DataType node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowAuthors)
     */
    @Override
    public void visit(ShowAuthors node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowBinaryLog)
     */
    @Override
    public void visit(ShowBinaryLog node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowBinLogEvent)
     */
    @Override
    public void visit(ShowBinLogEvent node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowCharaterSet)
     */
    @Override
    public void visit(ShowCharaterSet node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowCollation)
     */
    @Override
    public void visit(ShowCollation node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowColumns)
     */
    @Override
    public void visit(ShowColumns node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowContributors)
     */
    @Override
    public void visit(ShowContributors node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowCreate)
     */
    @Override
    public void visit(ShowCreate node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowDatabases)
     */
    @Override
    public void visit(ShowDatabases node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowEngine)
     */
    @Override
    public void visit(ShowEngine node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowEngines)
     */
    @Override
    public void visit(ShowEngines node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowErrors)
     */
    @Override
    public void visit(ShowErrors node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowEvents)
     */
    @Override
    public void visit(ShowEvents node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowFunctionCode)
     */
    @Override
    public void visit(ShowFunctionCode node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowFunctionStatus)
     */
    @Override
    public void visit(ShowFunctionStatus node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowGrants)
     */
    @Override
    public void visit(ShowGrants node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowIndex)
     */
    @Override
    public void visit(ShowIndex node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowMasterStatus)
     */
    @Override
    public void visit(ShowMasterStatus node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowOpenTables)
     */
    @Override
    public void visit(ShowOpenTables node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowPlugins)
     */
    @Override
    public void visit(ShowPlugins node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowPrivileges)
     */
    @Override
    public void visit(ShowPrivileges node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowProcedureCode)
     */
    @Override
    public void visit(ShowProcedureCode node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowProcedureStatus)
     */
    @Override
    public void visit(ShowProcedureStatus node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowProcesslist)
     */
    @Override
    public void visit(ShowProcesslist node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowProfile)
     */
    @Override
    public void visit(ShowProfile node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowProfiles)
     */
    @Override
    public void visit(ShowProfiles node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowSlaveHosts)
     */
    @Override
    public void visit(ShowSlaveHosts node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowSlaveStatus)
     */
    @Override
    public void visit(ShowSlaveStatus node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowStatus)
     */
    @Override
    public void visit(ShowStatus node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowTables)
     */
    @Override
    public void visit(ShowTables node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowTableStatus)
     */
    @Override
    public void visit(ShowTableStatus node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowTriggers)
     */
    @Override
    public void visit(ShowTriggers node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowVariables)
     */
    @Override
    public void visit(ShowVariables node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.ShowWarnings)
     */
    @Override
    public void visit(ShowWarnings node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.ddl.DescTableStatement)
     */
    @Override
    public void visit(DescTableStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.DALSetStatement)
     */
    @Override
    public void visit(DALSetStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.DALSetNamesStatement)
     */
    @Override
    public void visit(DALSetNamesStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dal.DALSetCharacterSetStatement)
     */
    @Override
    public void visit(DALSetCharacterSetStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dml.DMLCallStatement)
     */
    @Override
    public void visit(DMLCallStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dml.DMLDeleteStatement)
     */
    @Override
    public void visit(DMLDeleteStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dml.DMLInsertStatement)
     */
    @Override
    public void visit(DMLInsertStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dml.DMLReplaceStatement)
     */
    @Override
    public void visit(DMLReplaceStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dml.DMLSelectStatement)
     */
    @Override
    public void visit(DMLSelectStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dml.DMLSelectUnionStatement)
     */
    @Override
    public void visit(DMLSelectUnionStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.dml.DMLUpdateStatement)
     */
    @Override
    public void visit(DMLUpdateStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.mts.MTSSetTransactionStatement)
     */
    @Override
    public void visit(MTSSetTransactionStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.mts.MTSSavepointStatement)
     */
    @Override
    public void visit(MTSSavepointStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.mts.MTSReleaseStatement)
     */
    @Override
    public void visit(MTSReleaseStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.mts.MTSRollbackStatement)
     */
    @Override
    public void visit(MTSRollbackStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.ddl.DDLTruncateStatement)
     */
    @Override
    public void visit(DDLTruncateStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.ddl.DDLAlterTableStatement)
     */
    @Override
    public void visit(DDLAlterTableStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.ddl.DDLCreateIndexStatement)
     */
    @Override
    public void visit(DDLCreateIndexStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.ddl.DDLCreateTableStatement)
     */
    @Override
    public void visit(DDLCreateTableStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.ddl.DDLRenameTableStatement)
     */
    @Override
    public void visit(DDLRenameTableStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.ddl.DDLDropIndexStatement)
     */
    @Override
    public void visit(DDLDropIndexStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.ddl.DDLDropTableStatement)
     */
    @Override
    public void visit(DDLDropTableStatement node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.extension.ExtDDLCreatePolicy)
     */
    @Override
    public void visit(ExtDDLCreatePolicy node) {
    }

    /** 
     * @see io.candice.parser.visitor.SQLASTVisitor#visit(io.candice.parser.ast.stmt.extension.ExtDDLDropPolicy)
     */
    @Override
    public void visit(ExtDDLDropPolicy node) {
    }

}
