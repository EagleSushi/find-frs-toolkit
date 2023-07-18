package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
	"sync"

	"github.com/vfdizon/ranges/fileanalysis"
)

func main() {

	inputReader := bufio.NewReader(os.Stdin)

	fmt.Println("Please enter the .BED file path:")
	bedFilePath, _ := inputReader.ReadString('\n')
	bedFilePath = strings.TrimSpace(bedFilePath)

	fmt.Println("Please enter the directory path for the .gff3 files:")
	annotationDirectories, _ := inputReader.ReadString('\n')
	annotationDirectories = strings.TrimSpace(annotationDirectories)

	fmt.Println("Please enter the output file path: ( ending in .csv)")
	outputFilePath, _ := inputReader.ReadString('\n')
	outputFilePath = strings.TrimSpace(outputFilePath)

	annotations := fileanalysis.Annotations{
		Directory: annotationDirectories,
	}
	bedFile := fileanalysis.BEDFile{
		FileName: bedFilePath,
	}
	var waitGroup sync.WaitGroup
	waitGroup.Add(2)

	go annotations.AddAllAnnotations(&waitGroup)
	go bedFile.ReadLines(&waitGroup)

	waitGroup.Wait()
	csvWriter := fileanalysis.CSVWriter{
		FileName: outputFilePath,
	}

	annotationAnalyzer := fileanalysis.AnnotationAnalyzer{
		Annotations: &annotations,
		CSVWriter:   &csvWriter,
		BedFile:     &bedFile,
	}

	annotationAnalyzer.Analyze()

}
