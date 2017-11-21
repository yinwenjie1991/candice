package io.candice.parser.ast.expression.logical;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.PolyadicOperatorExpression;
import io.candice.parser.ast.expression.primary.literal.LiteralBoolean;
import io.candice.parser.util.ExprEvalUtils;
import io.candice.parser.visitor.SQLASTVisitor;

import java.util.Map;

public class LogicalAndExpression extends PolyadicOperatorExpression {
    public LogicalAndExpression() {
        super(PRECEDENCE_LOGICAL_AND);
    }

    @Override
    public String getOperator() {
        return "AND";
    }

    @Override
    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        for (Expression operand : operands) {
            Object val = operand.evaluation(parameters);
            if (val == null) return null;
            if (val == UNEVALUATABLE) return UNEVALUATABLE;
            if (!ExprEvalUtils.obj2bool(val)) {
                return LiteralBoolean.FALSE;
            }
        }
        return LiteralBoolean.TRUE;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

}
