package com.vdizon;

import java.util.Scanner;

import com.vdizon.dataProcessing.PrefixSearcher;
import com.vdizon.fileAnalysis.BEDFile;

public class App 
{
    public static void main( String[] args )
    {
        Scanner inputReader = new Scanner(System.in);

        System.out.println("Please input the bed file directory: ");
        String bedFileDirectory = inputReader.nextLine();

        System.out.println("Please input the output desired .csv file directory: ");
        String outputFileDirectory = inputReader.nextLine();

        System.out.println("How many groups are you looking for? (Not including the \"Other\" group)");
        int numberOfGroups = inputReader.nextInt();
        String[] groupPrefixes = new String[numberOfGroups];

        for(int i = 0; i < numberOfGroups; i++) {
            System.out.println("Please input the prefix of group " + (i+1) + " (case sensitive seperated by semicolons): ");
            groupPrefixes[i] = inputReader.next();
        }

        BEDFile bedFile;
        PrefixSearcher prefixSearcher; 

        try{
            bedFile = new BEDFile(bedFileDirectory);
            prefixSearcher = new PrefixSearcher(bedFile,outputFileDirectory ,groupPrefixes);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        prefixSearcher.search();

        try {
            prefixSearcher.exportToCSV();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
         
    }
}
