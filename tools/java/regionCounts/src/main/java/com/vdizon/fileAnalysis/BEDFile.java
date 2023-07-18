package com.vdizon.fileAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.vdizon.dataProcessing.GenomeRegion;
import com.vdizon.utils.Timer;

public class BEDFile {
    private File mTargetFile; 
    private ArrayList<GenomeRegion> mGenomeRegions; 

    public BEDFile(String directory) throws FileNotFoundException{
        mGenomeRegions = new ArrayList<>();

        System.out.println("BEDFile: " + directory);
        mTargetFile = new File(directory);

        Scanner scanner = new Scanner(mTargetFile);
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            GenomeRegion genomeRegion = new GenomeRegion(line);
            mGenomeRegions.add(genomeRegion);
        }

        scanner.close();
        System.out.println("Finished Parsing BED File in: " + Timer.stop() + "ms");
    }

    public ArrayList<GenomeRegion> getGenomeRegions() {
        return mGenomeRegions;
    }

    

    
}