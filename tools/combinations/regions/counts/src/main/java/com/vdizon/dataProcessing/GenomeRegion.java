package com.vdizon.dataProcessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.vdizon.utils.IntegerHolder;

public class GenomeRegion {
    private String mRegion;
    private boolean[] mContainsTypes;
    private IntegerHolder[] mTypeCount;
    private int mNumberOfTypes;

    public GenomeRegion(String region, int numberOfTypes) {
        mRegion = region;
        mContainsTypes = new boolean[numberOfTypes + 1];
        mNumberOfTypes = numberOfTypes + 1;
        mTypeCount = new IntegerHolder[numberOfTypes + 1];

        for(int i = 0; i < mNumberOfTypes; i++) {
            mTypeCount[i] = new IntegerHolder(0);
        }
    }

    public String getRegion() {
        return mRegion;
    }

    public int getRegionAsInt() {
        return Integer.parseInt(mRegion);
    }

    public boolean[] getContainsTypes() {
        return mContainsTypes;
    }

    public void setContainsType(int index) {
        mContainsTypes[index] = true;
        
    }

    public void addTypeCount(int index) {
        mTypeCount[index].addOne();
    }

    public String getAsCSVString() {
        String ret_val = mRegion;
        String containsTypes = "";
        int count = 0;

        for (IntegerHolder i : mTypeCount) {
            containsTypes += "," + i.getValue();
            count += i.getValue();
        }

        return ret_val + containsTypes + "," + count;
    }




    
}
