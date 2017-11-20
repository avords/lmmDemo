package com.lmm.bintree;

import java.math.BigInteger;

/**
 * Created by Administrator on 2017/7/28.
 */
public class Feibonaqie {
    public static int calSum(int n){
        if(n==1){
            return 0;
        }
        if(n==2){
            return 1;
        }
        return calSum(n-1)+calSum(n-2);
    }
    public static BigInteger calSum1(int n){
        if(n==1){
            return BigInteger.valueOf(0);
        }
        if(n==2){
            return BigInteger.valueOf(1);
        }
        BigInteger a = BigInteger.valueOf(0);
        BigInteger b = BigInteger.valueOf(1);
        for(int i=3;i<=n;i++){
           b = a.add(b); 
           a = b.subtract(a);
            //System.out.println(BigDecimal.valueOf(a.longValue()).divide(BigDecimal.valueOf(b.longValue())).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        return b;
    }
    public static void main(String[] args) {
        System.out.println(calSum1(450));
    }
}
