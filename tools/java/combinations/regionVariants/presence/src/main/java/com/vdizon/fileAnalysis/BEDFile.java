package com.vdizon.fileAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BEDFile {
    public static char DELIMITER = ';';

    private File mBedFile;
    private ArrayList<String> mBedFileLines = new ArrayList<String>();

    public BEDFile(String bedFileDirectory) throws FileNotFoundException {
        mBedFile = new File(bedFileDirectory);

        Scanner bedFileScanner = new Scanner(mBedFile);
        
        while(bedFileScanner.hasNextLine()) {
            String line = bedFileScanner.nextLine();
            String filteredLine = line.replaceAll("\t", ";");

            String[] parsed_line = filteredLine.split(";");
            String name = parsed_line[0];
            
            String region = parsed_line[3].substring(2);

            mBedFileLines.add(name + DELIMITER + region);
        }
    }

    public ArrayList<String> getGenomes() {
        return mBedFileLines;
    }

}
