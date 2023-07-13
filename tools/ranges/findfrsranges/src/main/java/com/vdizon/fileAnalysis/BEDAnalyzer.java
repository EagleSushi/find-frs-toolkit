package com.vdizon.fileAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BEDAnalyzer {
    private HashMap<String, String> mRespectiveAnnotations;
    private ArrayList<String> mAnnotationsWithinRange;

    public BEDAnalyzer(String bedFilePath, String annotationsDirectory) throws FileNotFoundException {
        mRespectiveAnnotations = new HashMap<>();
        mAnnotationsWithinRange = new ArrayList<>();
        storeAnnotationFiles(annotationsDirectory);
        analyzeBEDFile(bedFilePath);

    }

    // Store all of the annotation files in a HashMap, which returns
    private void storeAnnotationFiles(String annotationsDirectory) {
        File annotations = new File(annotationsDirectory);
        File[] annotationsFiles = annotations.listFiles();

        for (File annotationFile : annotationsFiles) {
            String annotationFileName = annotationFile.getName();
            if (!annotationFileName.endsWith(".gff3")) {
                continue;
            }

            String[] parsedFileName = annotationFileName.split("\\.");
            String annotationName = parsedFileName[0] + "." + parsedFileName[1];
            System.out.println("Annotation found: " + annotationName);
            mRespectiveAnnotations.put(annotationName, annotationFile.getAbsolutePath());
        }
    }

    private void analyzeBEDFile(String bedFilePath) throws FileNotFoundException {
        File bedFile = new File(bedFilePath);

        Scanner bedFileReader = new Scanner(bedFile);
        while (bedFileReader.hasNextLine()) {
            String line = bedFileReader.nextLine();

            String[] parsedLine;


            String[] parsed_name;
            String annotationName;
            try {
                parsedLine = line.split("\t");

                parsed_name = parsedLine[0].split("\\.");
                annotationName = parsed_name[0] + "." + parsed_name[1];                
            } catch (ArrayIndexOutOfBoundsException e) {
                continue;
            }            


            if (!mRespectiveAnnotations.containsKey(annotationName)) {
               continue;
            }

            int start = Integer.parseInt(parsedLine[1]);
            int end = Integer.parseInt(parsedLine[2]);
            String annotationFileName = mRespectiveAnnotations.get(annotationName);

            if (isWithinRange(start, end, annotationFileName)) {
                // System.out.println("Line: " + line + annotationName);
                mAnnotationsWithinRange.add(line + "\t" + annotationName);
            }
        }

        bedFileReader.close();
    }

    private boolean isWithinRange(int start, int end, String annotationFileName) throws FileNotFoundException {
        File annotationFile = new File(annotationFileName);
        Scanner annotationFileReader = new Scanner(annotationFile);
        while (annotationFileReader.hasNextLine()) {
            String line = annotationFileReader.nextLine();
            if (line.startsWith("#")) {
                continue;
            }
            String[] parsedLine = line.split("\t");

            int annotationStart = Integer.parseInt(parsedLine[3]);
            int annotationEnd = Integer.parseInt(parsedLine[4]);

            if (start >= annotationStart && end <= annotationEnd) {
                annotationFileReader.close();
                System.out.println(start + " " + end + " :: " + line );
                return true;
            }
        }

        annotationFileReader.close();

        return false;
    }

}
