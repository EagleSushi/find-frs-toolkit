package fileanalysis

import (
	"bufio"
	"fmt"
	"os"
)

type CSVWriter struct {
	FileName string
}

func (csvWriter *CSVWriter) WriteLines(header string, lines []string) {
	file, createError := os.Create(csvWriter.FileName)

	check(createError)
	defer file.Close()

	writer := bufio.NewWriter(file)
	defer writer.Flush()

	writer.WriteString(header)
	for _, line := range lines {
		writer.WriteString("\n" + line)
	}

	fmt.Println("Wrote to", csvWriter.FileName)
}
