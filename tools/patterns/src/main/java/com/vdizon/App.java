package com.vdizon;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import com.vdizon.fileAnalysis.BEDFile;
import com.vdizon.fileAnalysis.CSVBuilder;
import com.vdizon.utils.Timer;

public class App {
    public static void main(String[] args ) {
        String bedFileDirectory;
        String outputDirectory;

        if(args.length == 2) {
            bedFileDirectory = args[0];
            outputDirectory = args[1];
        } else {
            Scanner inputReader = new Scanner(System.in);
            System.out.println("Please enter the directory of the BED file");
            bedFileDirectory = inputReader.nextLine();

            System.out.println("Please enter the desired directory of the output CSV file");
            outputDirectory = inputReader.nextLine();
            inputReader.close();
        }

        BEDFile bedFile;
        CSVBuilder csvBuilder;

        Timer.start();
        try {
            bedFile = new BEDFile(bedFileDirectory);
            csvBuilder = new CSVBuilder(outputDirectory);

            csvBuilder.addHeader("Genome ID:", "Genome Start:", "Genome End:", "Frequented Regions:");
            for (String genome : bedFile.getDuplicateGenomes()) {
                String[] parsed_string = genome.split(BEDFile.DELIMITER + "");
                csvBuilder.writeRow(parsed_string[0], parsed_string[1], parsed_string[2], bedFile.getGenomeRegion(genome));
            }

            csvBuilder.build();
        } catch (FileNotFoundException e) {
            System.out.println("BED file not found");
            return;
        } catch (IOException e) {
            System.out.println("Error writing to CSV file");
            return;
        }

        System.out.println("Parsed BED file in " + Timer.stop() + "ms");

    }

}
