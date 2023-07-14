package file_analysis

import (
	"bufio"
	"errors"
	"fmt"
	"os"
	"time"
)

type BEDFile struct {
	Lines    []string
	FileName string
}

func (bedFile *BEDFile) ReadLines() error {
	if bedFile.FileName == "" {
		return errors.New("No .bed file specified")
	}
	bedFile.Lines = []string{}

	file, err := os.Open(bedFile.FileName)
	check(err)

	start := time.Now()
	fileScanner := bufio.NewScanner(file)
	fileScanner.Split(bufio.ScanLines)
	for fileScanner.Scan() {
		bedFile.Lines = append(bedFile.Lines, fileScanner.Text())
	}

	fmt.Println("Read BED File in", time.Since(start))

	file.Close()
	return nil
}

func (bedFile *BEDFile) PrintLines() {
	for index, value := range bedFile.Lines {
		fmt.Println(index, value)
	}
}

func check(err error) bool {
	ret_val := err != nil

	if ret_val {
		panic(err)
	}

	return ret_val
}
