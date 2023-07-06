package com.vdizon;

import java.io.IOException;
import com.vdizon.dataProcessing.RegionCounter;
import com.vdizon.utils.Timer;

public class App {

    public static void main(String[] args) {
        String bedFileDirectory;
        String csvFileDirectory;

        if (args.length != 2) {
            System.out.println("Please enter the directory of the BED file: ");
            bedFileDirectory = System.console().readLine();
            System.out.println("Please enter the directory of the CSV file: (Doesn't have to end with .csv) ");
            csvFileDirectory = System.console().readLine();
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

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + Timer.stop() + "ms");

    }
}
