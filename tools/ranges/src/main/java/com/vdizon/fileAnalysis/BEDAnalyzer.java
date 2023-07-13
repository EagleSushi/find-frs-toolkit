package com.vdizon.fileAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.vdizon.utils.Timer;

public class BEDAnalyzer {
    private HashMap<String, Annotation> mRespectiveAnnotations;
    private ArrayList<String> mAnnotationsWithinRange;
    public CSVBuilder mCSVBuilder;

    public BEDAnalyzer(String bedFilePath, String annotationsDirectory, String outputDirectory) throws FileNotFoundException, IOException {
        mRespectiveAnnotations = new HashMap<>();
        mAnnotationsWithinRange = new ArrayList<>();
        mCSVBuilder = new CSVBuilder(outputDirectory);
        storeAnnotationFiles(annotationsDirectory);
        long analyzeStartTime = Timer.stop();
        analyzeBEDFile(bedFilePath);
        System.out.println("Finished analyzing .bed file in: " + (Timer.stop() - analyzeStartTime) + "ms");
    }

    public void exportToCSV() throws IOException {
       for(String annotationWithinRange : mAnnotationsWithinRange) {
           mCSVBuilder.writeRow(annotationWithinRange);
       }
       mCSVBuilder.build();
    }

    // Store all of the annotation files in a HashMap, which returns
    private void storeAnnotationFiles(String annotationsDirectory) throws FileNotFoundException{
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
            mRespectiveAnnotations.put(annotationName, new Annotation(annotationName, annotationFile.getAbsolutePath()));
        }

        System.out.println("Finished parsing annotation files in: " + Timer.stop() + "ms");
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
            Annotation annotation = mRespectiveAnnotations.get(annotationName);
            String annotationLine = annotation.withinRangeLine(start, end);
            if (annotationLine != null) {
                mAnnotationsWithinRange.add(line.replace("\t", ",") + "," + annotationLine.replace("\t", ","));
            }
        }

        bedFileReader.close();
    }

}
