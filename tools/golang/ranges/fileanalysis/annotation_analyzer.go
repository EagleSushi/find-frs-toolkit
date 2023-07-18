package fileanalysis

import (
	"fmt"
	"strconv"
	"strings"
	"sync"
	"time"
)

type AnnotationAnalyzer struct {
	Annotations *Annotations
	BedFile     *BEDFile
	CSVWriter   *CSVWriter
}

func (annotationAnalyzer *AnnotationAnalyzer) Analyze() {
	startTime := time.Now()
	annotationAnalyzer.CSVWriter.CreateFile("")
	var waitGroup sync.WaitGroup
	waitGroup.Add(len(annotationAnalyzer.Annotations.AnnotationFileFromName))
	writeChannel := make(chan string)

	for name := range annotationAnalyzer.Annotations.AnnotationFileFromName {
		go annotationAnalyzer.checkForBEDFileEntries(writeChannel, &waitGroup, name)
	}

	go func() {
		for line := range writeChannel {
			annotationAnalyzer.CSVWriter.WriteLine(line)
		}
	}()

	waitGroup.Wait()
	close(writeChannel)

	fmt.Println("Analyzed in", time.Since(startTime))

	annotationAnalyzer.CSVWriter.Close()
}

func (annotationAnalyzer *AnnotationAnalyzer) checkForBEDFileEntries(writeChannel chan string, waitGroup *sync.WaitGroup, name string) {
	startTime := time.Now()

	defer waitGroup.Done()

	fmt.Println("[goroutine] checkingForBEDfile entries for", name)

	for _, line := range annotationAnalyzer.BedFile.Lines {
		respectiveAnnotation := annotationAnalyzer.Annotations.AnnotationFileFromName[name]
		if !strings.HasPrefix(line, name) {
			continue
		}

		parsedLine := strings.Split(line, "\t")
		start, startError := strconv.Atoi(parsedLine[1])
		end, endError := strconv.Atoi(parsedLine[2])

		if startError != nil || endError != nil {
			fmt.Println("Error converting start or end to int. Start:", start, "End:", end)
			continue
		}

		for annotationStartEnd, annotationLine := range respectiveAnnotation.LinesSet {
			parsedAnnotationStartEnd := strings.Split(annotationStartEnd, "\t")
			annotationStart, annotationStartError := strconv.Atoi(parsedAnnotationStartEnd[0])
			annotationEnd, annotationEndError := strconv.Atoi(parsedAnnotationStartEnd[1])

			if annotationStartError != nil || annotationEndError != nil {
				fmt.Println("Error converting annotation start or end to int. Start:", annotationStart, "End:", annotationEnd)
				continue
			}

			if start >= annotationStart && end <= annotationEnd {
				outString := strings.Replace(line+"\t"+annotationLine, "\t", ",", -1)
				writeChannel <- outString
			}
		}

	}

	fmt.Println("[goroutine] checkingForBEDfile done for", name, "in", time.Since(startTime))

}
