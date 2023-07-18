package com.vdizon.dataProcessing;

import java.util.Comparator;

public class GenomeComparator implements Comparator<GenomeRegion> {

    @Override
    public int compare(GenomeRegion first, GenomeRegion second) {
        String[] parsed_first = first.getRegion().split(":");
        String[] parsed_second = second.getRegion().split(":");

        int firstMain = Integer.parseInt(parsed_first[0]);
        int firstSub = Integer.parseInt(parsed_first[1]);
        int secondMain = Integer.parseInt(parsed_second[0]);
        int secondSub = Integer.parseInt(parsed_second[1]);

        if (firstMain < secondMain) {
            return -1;
        } else if (firstMain > secondMain) {
            return 1;
        }

        if (firstSub < secondSub) {
            return -1;
        } else if (firstSub > secondSub) {
            return 1;
        }

        return 0;
    }

}
