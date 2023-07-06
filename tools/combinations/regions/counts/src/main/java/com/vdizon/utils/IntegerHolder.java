package com.vdizon.utils;

public class IntegerHolder {
    private int mValue;

    public IntegerHolder(int value) {
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        mValue = value;
    }

    public void add(int value) {
        mValue += value;
    }

    public void subtract(int value) {
        mValue -= value;
    }

    public void addOne() {
        mValue++;
    }

    public void subtractOne() {
        mValue--;
    }
}
