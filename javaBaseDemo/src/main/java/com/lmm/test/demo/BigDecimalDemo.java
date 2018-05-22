package com.lmm.test.demo;

import java.math.BigDecimal;

public class BigDecimalDemo {

    public static void main(String[] args) {
        BigDecimal bigDecimal = BigDecimal.valueOf(20.00);
        System.out.println(bigDecimal.stripTrailingZeros().toPlainString());

        BigDecimal bigDecimal1 = BigDecimal.valueOf(20);
        System.out.println(bigDecimal1.stripTrailingZeros().toPlainString());
    }
}
