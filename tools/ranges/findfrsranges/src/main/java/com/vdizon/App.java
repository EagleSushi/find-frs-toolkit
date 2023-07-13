package com.vdizon;

import java.io.FileNotFoundException;
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

        inputReader.close();
        Timer.start();

        System.out.println("Analyzing .bed file...");
        try {
            BEDAnalyzer bedAnalyzer = new BEDAnalyzer(bedFilePath, annotationsDirectory);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }

        System.out.println("Finished in " + Timer.stop() + " milliseconds.");
    }
}
