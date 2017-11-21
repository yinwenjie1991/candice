package io.candice.parser.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-06
 */
public interface BinaryOperandCalculator {
    Number calculate(Integer integer1, Integer integer2);

    Number calculate(Long long1, Long long2);

    Number calculate(BigInteger bigint1, BigInteger bigint2);

    Number calculate(BigDecimal bigDecimal1, BigDecimal bigDecimal2);
}
