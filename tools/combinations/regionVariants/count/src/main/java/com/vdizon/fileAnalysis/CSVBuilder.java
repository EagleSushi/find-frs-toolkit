package com.vdizon.fileAnalysis;

import java.io.FileWriter;
import java.io.IOException;

public class CSVBuilder {
    private String mDirectory; 
    private FileWriter mFileWriter;

    public CSVBuilder(String directory) throws IOException{
        mDirectory = directory;

        mFileWriter = new FileWriter(mDirectory);
    }

    public void build() throws IOException{
        mFileWriter.close();
        System.out.println("Wrote to CSV: " + mDirectory);
    }

    public void addHeader(String... headers) throws IOException{
        String headerString = "";

        for (String header : headers) {
            headerString += header + ",";
        }

        String writeString = headerString.substring(0, headerString.length() - 1) + "\n";
        mFileWriter.write(writeString);
    }

    public void skipLine() throws IOException{
        mFileWriter.write("\n");
    }

    public void writeRow(String... rowEntries) throws IOException{
        String rowString = "";
        for(String rowEntry : rowEntries) {
            rowString += rowEntry + ",";
        }
        String writeString = rowString.substring(0, rowString.length() - 1) + "\n";
        mFileWriter.write(writeString);
    }



}