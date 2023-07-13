package com.vdizon.utils;

public class Timer {
    private static long mStartTime;
    
    public static void start() {
        mStartTime = System.currentTimeMillis();
    }

    public static long stop() {
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - mStartTime;
        return elapsedTime;
    }
}
