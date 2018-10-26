package com.lmm.hystrix;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class TestHystrix {

    @Test
    public void maxCurrentRequst() throws InterruptedException {
        int count = 10;
        while (count > 0) {
            int id = count--;
            new Thread(() -> {
                try {
                    new CommandUsingSemaphoreIsolation(id).execute();
                } catch (Exception ex) {
                    System.out.println("Exception:" + ex.getMessage() + " id=" + id);
                }
            }).start();
        }

        TimeUnit.SECONDS.sleep(100);
    }

    @Test
    public void expTest() {
        assertEquals("Hello Failure Exception!", new CommandHelloFailure("Exception").execute());
    }

    @Test
    public void timeOutTest() {
        assertEquals("Hello Failure Timeout!", new CommandHelloFailure("Timeout").execute());
    }

    @Test
    public void rejectTest() throws InterruptedException {
        int count = 5;
        while (count-- > 0) {
            new CommandHelloFailure("Reject").queue();
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }

    public static void main(String[] args) {
        BigDecimal b1 = new BigDecimal("1.2");
        BigDecimal b2 = new BigDecimal("1.20000");

        System.out.println(b1.equals(b2));
    }
}
