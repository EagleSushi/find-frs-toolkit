package fileanalysis

import (
	"errors"
	"fmt"
	"os"
	"path/filepath"
	"strings"
	"sync"
)

type Annotations struct {
	Directory              string
	AnnotationFileFromName map[string]*AnnotationFile
}

func (annotations *Annotations) AddAllAnnotations(waitGroup *sync.WaitGroup) error {
	defer waitGroup.Done()
	defer fmt.Println("[goroutine] AddAllAnnotations done")

	fmt.Println("[goroutine] AddAllAnnotations starting")
	fmt.Print("\n")

	if annotations.Directory == "" {
		return errors.New("no directory specified")
	}

	annotations.AnnotationFileFromName = make(map[string]*AnnotationFile)

	directory, openErr := os.Open(annotations.Directory)
	files, readErr := directory.Readdir(0)

	if openErr != nil || readErr != nil {
		return errors.Join(openErr, readErr)
	}

	annotations.addGff3Files(annotations.Directory, files)

	for name := range annotations.AnnotationFileFromName {
		annotationFile := annotations.AnnotationFileFromName[name]
		check(annotationFile.ReadLines())
	}

	return nil
}

func (annotations *Annotations) addGff3Files(directory string, files []os.FileInfo) {
	for _, file := range files {
		fileName := file.Name()
		if strings.HasSuffix(fileName, ".gff3") {
			nameOfAnnotation := annotationName(fileName)
			filePath := filepath.Join(directory, fileName)
			respectiveAnnotation := AnnotationFile{
				FileName: filePath,
			}
			annotations.AnnotationFileFromName[nameOfAnnotation] = &respectiveAnnotation
		}
	}
}

func annotationName(fileName string) string {
	parsedString := strings.Split(fileName, ".")
	return parsedString[0] + "." + parsedString[1]
}
