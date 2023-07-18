package com.vdizon.utils;

public class Timer {
    private static long mStartTime;

    public static void start() {
        mStartTime = System.currentTimeMillis();
    }

    public static long stop() {
        return System.currentTimeMillis() - mStartTime;
    }
}
