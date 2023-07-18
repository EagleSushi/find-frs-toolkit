package com.vdizon.fileAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import com.vdizon.utils.StringHolder;
import com.vdizon.utils.Timer;


public class BEDFile {
    public static char DELIMITER = ';';
    private File mTargetFile;

    private HashSet<String> mDuplicateGenomes;
    private HashMap<String, StringHolder> mGenomeMap;
    private HashSet<String> mUniqueGenomes;
    
    public BEDFile(String directory) throws FileNotFoundException {
        mTargetFile = new File(directory);
        mDuplicateGenomes = new HashSet<>();
        mGenomeMap = new HashMap<>();
        mUniqueGenomes = new HashSet<>();
        

        Scanner scanner = new Scanner(mTargetFile);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parsed_line = line.split("\t");

            String name = parsed_line[0];
            String start = parsed_line[1];
            String end = parsed_line[2];
            String region = parsed_line[3];

            String genomeWithoutRegion = name + DELIMITER + start + DELIMITER + end;
            
            if(mUniqueGenomes.contains(genomeWithoutRegion)) {
                System.out.println("Duplicate found");
                StringHolder stringHolder = mGenomeMap.get(genomeWithoutRegion);
                mDuplicateGenomes.add(genomeWithoutRegion);
                stringHolder.add("," + region);
                System.out.println(mDuplicateGenomes.size());
            } else {
                mUniqueGenomes.add(genomeWithoutRegion);
                mGenomeMap.put(genomeWithoutRegion, new StringHolder(region));
            }
            

            
        }  

        scanner.close();

        System.out.println("Done in " + Timer.stop() + "ms");
    }

    public HashSet<String> getDuplicateGenomes() {
        return mDuplicateGenomes;
    }

    public String getGenomeRegion(String genome) {
        return mGenomeMap.get(genome).getValue();
    }

}
