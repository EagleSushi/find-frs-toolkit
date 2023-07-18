package com.vdizon.utils;

public class Timer {
    private static long startTime;

    public static void start() {
        startTime = System.currentTimeMillis();
    }

    public static long stop() {
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
