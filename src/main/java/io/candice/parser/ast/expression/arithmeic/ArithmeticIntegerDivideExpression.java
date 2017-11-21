package io.candice.parser.ast.expression.arithmeic;

import io.candice.parser.ast.expression.Expression;
import io.candice.parser.visitor.SQLASTVisitor;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ArithmeticIntegerDivideExpression extends ArithmeticBinaryOperatorExpression {
    public ArithmeticIntegerDivideExpression(Expression leftOprand, Expression rightOprand) {
        super(leftOprand, rightOprand, PRECEDENCE_ARITHMETIC_FACTOR_OP);
    }

    @Override
    public String getOperator() {
        return "DIV";
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }

    public Number calculate(Integer integer1, Integer integer2) {
        throw new UnsupportedOperationException();
    }

    public Number calculate(Long long1, Long long2) {
        throw new UnsupportedOperationException();
    }

    public Number calculate(BigInteger bigint1, BigInteger bigint2) {
        throw new UnsupportedOperationException();
    }

    public Number calculate(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        throw new UnsupportedOperationException();
    }
}
