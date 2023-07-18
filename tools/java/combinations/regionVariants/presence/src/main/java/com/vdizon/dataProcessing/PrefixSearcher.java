package com.vdizon.dataProcessing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.vdizon.fileAnalysis.BEDFile;
import com.vdizon.fileAnalysis.CSVBuilder;

public class PrefixSearcher {
    private HashMap<String, GenomeRegion> mGenomeRegions;
    private ArrayList<String> mGenomes;
    private String[] mGroupPrefixes;

    private CSVBuilder mCSVBuilder;

    public PrefixSearcher(BEDFile bedFile, String csvString, String[] groupPrefixes) throws IOException {
        mGenomeRegions = new HashMap<String, GenomeRegion>();
        mGenomes = bedFile.getGenomes();

        mGroupPrefixes = groupPrefixes;
        mCSVBuilder = new CSVBuilder(csvString);
    }

    public void search() {

        for (String s : mGenomes) {
            String[] parsed_string = s.split(String.valueOf(BEDFile.DELIMITER));
            String name = parsed_string[0];
            String region = parsed_string[1];


            if (!mGenomeRegions.containsKey(region)) {
                GenomeRegion genomeRegion = new GenomeRegion(region, mGroupPrefixes.length);
                mGenomeRegions.put(region, genomeRegion);
            }
            addContainsType(name, region);
        }
    }

    public void exportToCSV() throws IOException {
        String header = "Region:, ";

        for (int i = 0; i < mGroupPrefixes.length; i++) {
            header += "Group " + (i+1) + ": " + mGroupPrefixes[i] + ",";
        }

        header += "Group " + (mGroupPrefixes.length + 1) + ": Other";

        ArrayList<GenomeRegion> genomeRegions = new ArrayList<GenomeRegion>();

        for (GenomeRegion g : mGenomeRegions.values()) {
            genomeRegions.add(g);
        }

        Collections.sort(genomeRegions, new GenomeComparator());

        mCSVBuilder.addHeader(header);

        for(GenomeRegion g : genomeRegions) {
            mCSVBuilder.writeRow(g.getAsCSVString());
        }

        mCSVBuilder.build();

    }

    private void addContainsType(String name, String region) {
        GenomeRegion genomeRegion = mGenomeRegions.get(region);

        for (int i = 0; i < mGroupPrefixes.length; i++) {
            String searchedString = mGroupPrefixes[i];

            String[] parsed_string = searchedString.split(String.valueOf(BEDFile.DELIMITER));
            setGenomeRegionIfMatches(parsed_string, name, genomeRegion, i);
        }

    }

    private void setGenomeRegionIfMatches(String[] prefixes, String genomeName, GenomeRegion region, int index) {
        for (String s : prefixes) {
            if (genomeName.startsWith(s)) {
                region.setContainsType(index);
                return;
            }
        } 

        region.setContainsType(mGroupPrefixes.length);
    }

}
