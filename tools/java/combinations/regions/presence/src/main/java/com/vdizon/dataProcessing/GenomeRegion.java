package com.vdizon.dataProcessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class GenomeRegion {
    private String mRegion;
    private boolean[] mContainsTypes;
    private int mNumberOfTypes;

    public GenomeRegion(String region, int numberOfTypes) {
        mRegion = region;
        mContainsTypes = new boolean[numberOfTypes+1];
        mNumberOfTypes = numberOfTypes + 1;
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

    public String getAsCSVString() {
        String ret_val = mRegion;
        String containsTypes = "";
        
        HashSet<Boolean> set = new HashSet<Boolean>();
        for (boolean b : mContainsTypes) {
            set.add(b);
            containsTypes += "," + (b ? 1 : 0);
        }

        if(set.size() == 1 && set.contains(true)) {
            return ret_val + containsTypes + ",1";
        }

        return ret_val + containsTypes + ",0";
    }




    
}
