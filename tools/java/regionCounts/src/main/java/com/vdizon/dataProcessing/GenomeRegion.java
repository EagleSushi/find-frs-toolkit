package com.vdizon.dataProcessing;

public class GenomeRegion {
    private int mMainRegion;
    private int mSubRegion;
    
    public GenomeRegion(String inputLine) {
        String[] parsed_input_string = inputLine.split(" ");
        String region = parsed_input_string[parsed_input_string.length - 1];

        String[] parsed_region = region.split("fr");
        String[] split_region = parsed_region[1].split(":");
        mMainRegion = Integer.parseInt(split_region[0]);
        mSubRegion = Integer.parseInt(split_region[1]);
    }

    public int getMainRegion() {
        return mMainRegion;
    }

    public int getSubRegion() {
        return mSubRegion;
    }
}
