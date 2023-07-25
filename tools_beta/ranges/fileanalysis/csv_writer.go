package fileanalysis

import (
	"bufio"
	"fmt"
	"os"
)

type CSVWriter struct {
	FileName     string
	csvFile      *os.File
	writer       *bufio.Writer
	batchSize    int
	writeBatch   []string
	numOfBatches int
	Verbose      bool
}

func (csvWriter *CSVWriter) CreateFile(header string) {
	csvWriter.batchSize = 1e5
	csvWriter.numOfBatches = 0
	file, createError := os.Create(csvWriter.FileName)

	check(createError)

	csvWriter.writer = bufio.NewWriter(file)

	csvWriter.WriteLine(header)
	if csvWriter.Verbose {
		fmt.Println("Created", csvWriter.FileName)
		fmt.Print("\n")
		fmt.Println("Writing to", csvWriter.FileName, "concurrently with a batch size of", csvWriter.batchSize, "lines each")
		fmt.Print("\n")
	}
}

func (csvWriter *CSVWriter) WriteLine(line string) {
	csvWriter.writeBatch = append(csvWriter.writeBatch, line)
	if len(csvWriter.writeBatch) >= csvWriter.batchSize {
		csvWriter.flushBatch()
	}
}

func (csvWriter *CSVWriter) flushBatch() {
	for _, batchLine := range csvWriter.writeBatch {
		_, err := csvWriter.writer.WriteString(batchLine + "\n")
		if err != nil {
			if csvWriter.Verbose {
				fmt.Println("Error writing line to file:", err)
			}
			return
		}
	}
	csvWriter.numOfBatches++
	if csvWriter.Verbose {
		fmt.Println("Wrote batch", csvWriter.numOfBatches, "to file")
	}
	csvWriter.writer.Flush()
	csvWriter.csvFile.Sync()
	csvWriter.writeBatch = []string{}

}

func (csvWriter *CSVWriter) Close() {
	if csvWriter.Verbose {
		fmt.Println("Finished analysis, flushing remaining batch lines")
	}
	csvWriter.flushBatch()
	csvWriter.csvFile.Close()

	if csvWriter.Verbose {
		fmt.Println("Closed", csvWriter.FileName)
	}
}
