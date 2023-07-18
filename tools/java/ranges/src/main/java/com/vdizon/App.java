package com.vdizon;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import com.vdizon.fileAnalysis.BEDAnalyzer;
import com.vdizon.utils.Timer;

public class App {
    public static void main(String[] args) {
        Scanner inputReader = new Scanner(System.in);

        System.out.println("Please enter .bed file path: ");
        String bedFilePath = inputReader.nextLine();

        System.out.println("Please enter the directory of all of the annotations (.gff3) files");
        String annotationsDirectory = inputReader.nextLine();

        System.out.println("Please enter the output directory for the CSV file");
        String outputDirectory = inputReader.nextLine();

        inputReader.close();
        Timer.start();

        System.out.println("Analyzing .bed file...");
        try {
            BEDAnalyzer bedAnalyzer = new BEDAnalyzer(bedFilePath, annotationsDirectory, outputDirectory);
            bedAnalyzer.exportToCSV();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error creating CSV output file.");
        }

        System.out.println("Finished in " + Timer.stop() + " milliseconds.");
    }
}
