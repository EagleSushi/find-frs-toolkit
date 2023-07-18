package com.vdizon.dataProcessing;

import java.util.ArrayList;
import java.util.HashMap;

import com.vdizon.utils.IntegerHolder;

public class ProcessedRegion {
    
    private int mRegionNumber; 
    private IntegerHolder mRegionCount;
    private ArrayList<Integer> mSubRegions;

    private HashMap<Integer, IntegerHolder> mSubRegionCount;

    public ProcessedRegion (int regionNumber) {
        mRegionNumber = regionNumber;
        mRegionCount = new IntegerHolder(1);

        mSubRegionCount = new HashMap<>();
        mSubRegions = new ArrayList<>();
    }

    public String getRegionCountCSV() {
        return "fr" + mRegionNumber + ", " + mRegionCount.getValue();
    }

    public String getSubregionsCSV() {
        String ret_val = "fr" + mRegionNumber + ", ";
        for(int subRegion : mSubRegions) {
            ret_val +=  subRegion + ", ";
        }

        return ret_val.substring(0, ret_val.length() - 2);
    }

    public String[] getIndividualSubregionsCSV() {
        String[] ret_val = new String[mSubRegions.size()];

        for(int i = 0; i < mSubRegions.size(); i++) {
            int subRegion = mSubRegions.get(i);
            ret_val[i] = "fr" + mRegionNumber + ":" + subRegion + ", " + getSubRegionCount(subRegion);
        }

        return ret_val;
    }

    public void addToRegionCount() {
        mRegionCount.addOne();
    }

    public void addSubRegion(int subRegion) {
        mSubRegions.add(subRegion);
    }

    public IntegerHolder getCount() {
        return mRegionCount;
    }

    public ArrayList<Integer> getSubRegions() {
        return mSubRegions;
    }

    public int getRegionNumber() {
        return mRegionNumber;
    }

    public void addToSubregion(int subRegion) {
        IntegerHolder integerHolder = mSubRegionCount.get(subRegion);
        if (integerHolder == null) {
            integerHolder = new IntegerHolder(1);
            mSubRegionCount.put(subRegion, integerHolder);
            addSubRegion(subRegion);
        } else {
            integerHolder.addOne();
        }
    }

    public int getSubRegionCount(int subRegion) {
        IntegerHolder integerHolder = mSubRegionCount.get(subRegion);
        if (integerHolder == null) {
            return 0;
        } else {
            return integerHolder.getValue();
        }
    }


}
