package main

import (
	"flag"
	"strings"
	"sync"

	"github.com/vfdizon/ranges/fileanalysis"
)

var (
	bedFilePathOption        string //i
	annotationFilePathOption string //p
	outputFilePathOption     string //o
	verboseOption            bool   //verbose
)

func init() {
	flag.StringVar(&bedFilePathOption, "i", "", "Path to the BED file")
	flag.StringVar(&annotationFilePathOption, "p", "", "Path to the directory containing the GFF3 files")
	flag.StringVar(&outputFilePathOption, "o", "", "Path to the output file")
	flag.BoolVar(&verboseOption, "verbose", false, "Verbose output")
}

func main() {
	flag.Parse()
	bedFilePath := strings.TrimSpace(bedFilePathOption)

	annotationDirectories := strings.TrimSpace(annotationFilePathOption)

	outputFilePath := strings.TrimSpace(outputFilePathOption)

	annotations := fileanalysis.Annotations{
		Directory: annotationDirectories,
		Verbose:   verboseOption,
	}
	bedFile := fileanalysis.BEDFile{
		FileName: bedFilePath,
		Verbose:  verboseOption,
	}
	var waitGroup sync.WaitGroup
	waitGroup.Add(2)

	go annotations.AddAllAnnotations(&waitGroup)
	go bedFile.ReadLines(&waitGroup)

	waitGroup.Wait()
	csvWriter := fileanalysis.CSVWriter{
		FileName: outputFilePath,
		Verbose:  verboseOption,
	}

	annotationAnalyzer := fileanalysis.AnnotationAnalyzer{
		Annotations: &annotations,
		CSVWriter:   &csvWriter,
		BedFile:     &bedFile,
		Verbose:     verboseOption,
	}

	annotationAnalyzer.Analyze()

}
