package io.candice.parser.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-06
 */
public interface UnaryOperandCalculator {
    Number calculate(Integer num);

    Number calculate(Long num);

    Number calculate(BigInteger num);

    Number calculate(BigDecimal num);
}
