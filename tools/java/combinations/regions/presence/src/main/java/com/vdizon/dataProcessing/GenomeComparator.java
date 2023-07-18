package com.vdizon.dataProcessing;

import java.util.Comparator;

public class GenomeComparator implements Comparator<GenomeRegion>{

    @Override
    public int compare(GenomeRegion first, GenomeRegion second) {
        if(first.getRegionAsInt() < second.getRegionAsInt()) {
            return -1;
        } else if(first.getRegionAsInt() > second.getRegionAsInt()) {
            return 1;
        } else {
            return 0;
        }
    }
    
}
