package com.vdizon.fileAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import com.vdizon.utils.Timer;

public class Annotation {
    public static final String DELIMITER =  ":";
    private HashMap<String, String> mStartEndToLine;
    private String mAnnotationName;

    public Annotation(String annotationName, String annotationDirectory) throws FileNotFoundException {
        mAnnotationName = annotationName;
        mStartEndToLine = new HashMap<>();

        Scanner annotationFileReader = new Scanner(new File(annotationDirectory));
        long startTime = Timer.stop();
        while(annotationFileReader.hasNext()) {
            String line = annotationFileReader.nextLine();
            if(line.startsWith("#")) {
                continue;
            }
            String[] parsedLine = line.split("\t");
            String startEnd = parsedLine[3] + DELIMITER + parsedLine[4];

            if(mStartEndToLine.containsKey(startEnd)) {
                continue;
            }
            mStartEndToLine.put(startEnd, line);
        }
        System.out.println("Finished parsing annotation file: " + annotationName + " in: " + (Timer.stop()-startTime) + "ms");
        annotationFileReader.close();
        
    }

    public String withinRangeLine(int start, int end) {
        for(String startEnd : mStartEndToLine.keySet()) {
            String[] parsedStartEnd = startEnd.split(DELIMITER);
            int parsedStart = Integer.parseInt(parsedStartEnd[0]);
            int parsedEnd = Integer.parseInt(parsedStartEnd[1]);
            if(start >= parsedStart && end <= parsedEnd) {
                return mStartEndToLine.get(startEnd);
            }
        }

        return null;
    }
}
