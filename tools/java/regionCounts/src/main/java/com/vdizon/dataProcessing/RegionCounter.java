package com.vdizon.dataProcessing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.vdizon.fileAnalysis.BEDFile;
import com.vdizon.fileAnalysis.CSVBuilder;
import com.vdizon.utils.Timer;

public class RegionCounter {
    private BEDFile mBedFile;
    private CSVBuilder mfrCountsCSV;
    private CSVBuilder mfrVariantsCSV;
    private CSVBuilder mfrVarCountCSV;
    private ArrayList<GenomeRegion> mGenomeRegions;

    private HashSet<Integer> mMainRegions;
    private HashMap<Integer, ProcessedRegion> mRegions;


    public RegionCounter(String bedFileDirectory, String csvFileDirectory) {
        try {
            mBedFile = new BEDFile(bedFileDirectory);
            mfrCountsCSV = new CSVBuilder(csvFileDirectory + "_regionCounts.csv");
            mfrVariantsCSV = new CSVBuilder(csvFileDirectory + "_regionVariants.csv");
            mfrVarCountCSV = new CSVBuilder(csvFileDirectory + "_regionVariantCounts.csv");
        }  catch (FileNotFoundException e) {
            System.out.println("!!Error Finding BED File!! Stacktrace: ");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("!!Error Creating CSV File!! Stacktrace: ");
            e.printStackTrace();
        }
        
        mGenomeRegions = mBedFile.getGenomeRegions();

        mRegions = new HashMap<>();
        mMainRegions = new HashSet<>();

    }

    public void search() {
        for (GenomeRegion genomeRegion : mGenomeRegions) {
            int mainRegion = genomeRegion.getMainRegion();
            int subRegion = genomeRegion.getSubRegion();

            addMainRegion(mainRegion);
            addSubRegion(mainRegion, subRegion);
        }
    }

    public void exportToCSV() throws IOException{

        //csv 1, region count
        mfrCountsCSV.addHeader("Region: ", "Count: ");
        for(int mainRegion : mMainRegions) {
            ProcessedRegion processedRegion = mRegions.get(mainRegion);
            mfrCountsCSV.writeRow(processedRegion.getRegionCountCSV());
        }
        mfrCountsCSV.build();


        //csv 2, each subregion
        mfrVariantsCSV.addHeader("Region: ", "Subregions: ");
        for(int mainRegion: mMainRegions) {
            ProcessedRegion processedRegion = mRegions.get(mainRegion);
            mfrVariantsCSV.writeRow(processedRegion.getSubregionsCSV());
        }
        mfrVariantsCSV.build();

        //csv 3, each individual subregion count
        mfrVarCountCSV.skipLine();
        mfrVarCountCSV.addHeader("Region: ", "Count: ");
        for(ProcessedRegion pr : mRegions.values()) {
            for(String s : pr.getIndividualSubregionsCSV()) {
                mfrVarCountCSV.writeRow(s);
            }
        }
        
        mfrVarCountCSV.build();
    }

    private void addMainRegion(int mainRegion) {
        if(mMainRegions.contains(mainRegion)) {
            ProcessedRegion processedRegion = mRegions.get(mainRegion);
            processedRegion.addToRegionCount();
        } else {
            mMainRegions.add(mainRegion);
            mRegions.put(mainRegion, new ProcessedRegion(mainRegion));
        }
    }

    private void addSubRegion(int mainRegion, int subRegion) {
        ProcessedRegion processedRegion = mRegions.get(mainRegion);
        processedRegion.addToSubregion(subRegion);
    }

}
