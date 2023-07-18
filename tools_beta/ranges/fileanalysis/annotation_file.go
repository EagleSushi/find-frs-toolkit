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
	LinesSet map[string]bool
	FileName string
}

func (annotationFile *AnnotationFile) ReadLines() error {
	if annotationFile.FileName == "" {
		return errors.New("no annotation file specified")
	}
	annotationFile.LinesSet = make(map[string]bool)
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
			annotationFile.addAnnotationIfDoesntExist(currentLine)
		}
	}
	file.Close()

	fmt.Println("Read Annotation File:", annotationFile.FileName, "in", time.Since(startTime))
	return nil
}

func (annotationFile *AnnotationFile) addAnnotationIfDoesntExist(line string) {
	annotationFile.LinesSet[line] = true
}
