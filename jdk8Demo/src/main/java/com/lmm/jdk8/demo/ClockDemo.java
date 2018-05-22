package com.lmm.jdk8.demo;

import java.time.Clock;

public class ClockDemo {
    public static void main(String[] args) {
        Clock clock = Clock.systemUTC();
        System.out.println(clock.millis());
        System.out.println(System.currentTimeMillis());
        Clock defaultClock = Clock.systemDefaultZone();
        System.out.println(defaultClock.instant());
        
    }
}
