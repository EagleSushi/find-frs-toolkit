package fileanalysis

import (
	"bufio"
	"errors"
	"fmt"
	"os"
	"strings"
	"time"
)

type AnnotationFile struct {
	LinesSet map[string]string
	FileName string
}

func (annotationFile *AnnotationFile) ReadLines() error {
	if annotationFile.FileName == "" {
		return errors.New("no annotation file specified")
	}
	annotationFile.LinesSet = make(map[string]string)
	startTime := time.Now()
	file, err := os.Open(annotationFile.FileName)
	check(err)

	fileScanner := bufio.NewScanner(file)
	fileScanner.Split(bufio.ScanLines)
	for fileScanner.Scan() {
		currentLine := fileScanner.Text()

		if strings.HasPrefix(currentLine, "#") {
			continue
		} else {
			parsedLine := strings.Split(currentLine, "\t")

			startEnd := parsedLine[3] + "\t" + parsedLine[4]
			annotationFile.addAnnotationIfDoesntExist(currentLine, startEnd)
		}
	}
	file.Close()

	fmt.Println("Read Annotation File:", annotationFile.FileName, "in", time.Since(startTime))
	return nil
}

func (annotationFile *AnnotationFile) addAnnotationIfDoesntExist(line, startEnd string) bool {
	_, contains := annotationFile.LinesSet[startEnd]

	if !contains {
		annotationFile.LinesSet[startEnd] = line
		return false
	}

	return true

}
