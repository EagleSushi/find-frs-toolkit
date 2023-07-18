package main

import (
	"sync"

	"github.com/vfdizon/bedReader/fileanalysis"
)

func main() {
	annotations := fileanalysis.Annotations{
		Directory: "C:\\Users\\Vincent Dizon\\Documents\\Github Repositories\\find-frs-toolkit\\sample\\data",
	}
	bedFile := fileanalysis.BEDFile{
		FileName: "C:\\Users\\Vincent Dizon\\Documents\\Github Repositories\\find-frs-toolkit\\sample\\data\\sampleData.bed",
	}
	var waitGroup sync.WaitGroup
	waitGroup.Add(2)

	go annotations.AddAllAnnotations(&waitGroup)
	go bedFile.ReadLines(&waitGroup)

	waitGroup.Wait()

	annotationAnalyzer := fileanalysis.AnnotationAnalyzer{
		Annotations: &annotations,
		BedFile:     &bedFile,
	}

	annotationAnalyzer.Analyze()

}
