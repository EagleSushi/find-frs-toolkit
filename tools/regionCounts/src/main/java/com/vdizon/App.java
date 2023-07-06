package com.vdizon;

import java.io.IOException;
import java.util.Scanner;

import com.vdizon.dataProcessing.RegionCounter;
import com.vdizon.utils.Timer;

public class App {

    public static void main(String[] args) {
        String bedFileDirectory;
        String csvFileDirectory;

        if (args.length != 2) {
            Scanner inputReader = new Scanner(System.in);
            System.out.println("Please enter the directory of the BED file: ");
            bedFileDirectory = inputReader.nextLine();
            System.out.println("Please enter the directory of the CSV file: (Doesn't have to end with .csv) ");
            csvFileDirectory = inputReader.nextLine();
            inputReader.close();
        } else {
            bedFileDirectory = args[0];
            csvFileDirectory = args[1];
        }

        if (bedFileDirectory.endsWith(".bed") == false) {
            System.out.println("BED file must end with .bed");
            return;
        }

        Timer.start();

        RegionCounter regionCounter = new RegionCounter(bedFileDirectory, csvFileDirectory);
        regionCounter.search();
        try {
            regionCounter.exportToCSV();
        } catch (IOException e) {
            System.out.println("Failed to export to CSV. Stacktrace: ");
            e.printStackTrace();
        }

        System.out.println("Time taken: " + Timer.stop() + "ms");

    }
}
