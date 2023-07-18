package com.vdizon.utils;

public class StringHolder {
    private String mValue;

    public StringHolder(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

    public void add(String other) {
        mValue += other;
    }

}
