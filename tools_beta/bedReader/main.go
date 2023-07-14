package main

import (
	"github.com/vfdizon/bedReader/file_analysis"
)

func main() {
	bedFile := file_analysis.BEDFile{
		FileName: "C:\\Users\\Vincent Dizon\\Documents\\Github Repositories\\find-frs-toolkit\\sample\\data\\sampleData.bed",
	}

	bedFile.ReadLines()
	bedFile.PrintLines()
}
