package fileanalysis

import (
	"fmt"
	"strconv"
	"strings"
	"sync"
	"time"
)

type AnnotationAnalyzer struct {
	Annotations      *Annotations
	BedFile          *BEDFile
	linesWithinRange []string
}

func (annotationAnalyzer *AnnotationAnalyzer) Analyze() {
	annotationAnalyzer.linesWithinRange = []string{}
	startTime := time.Now()

	var waitGroup sync.WaitGroup
	waitGroup.Add(len(annotationAnalyzer.Annotations.AnnotationFileFromName))
	channel := make(chan string)

	for name := range annotationAnalyzer.Annotations.AnnotationFileFromName {
		go annotationAnalyzer.checkForBEDFileEntries(channel, &waitGroup, name)
	}

	go func() {
		for line := range channel {
			annotationAnalyzer.linesWithinRange = append(annotationAnalyzer.linesWithinRange, line)
		}
	}()

	waitGroup.Wait()
	close(channel)

	fmt.Println("Analyzed in", time.Since(startTime))
}

func (annotationAnalyzer *AnnotationAnalyzer) checkForBEDFileEntries(channel chan string, waitGroup *sync.WaitGroup, name string) {
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
				channel <- strings.Replace(line+"\t"+annotationLine, "\t", ",", -1)
			}
		}

	}

	fmt.Println("[goroutine] checkingForBEDfile done for", name, "in", time.Since(startTime))

}

func (annotationAnalyzer *AnnotationAnalyzer) ExportToCSV(csvPath string) {
	csvWriter := CSVWriter{
		FileName: csvPath,
	}

	csvWriter.WriteLines("", annotationAnalyzer.linesWithinRange)
}
