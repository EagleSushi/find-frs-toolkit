package fileanalysis

import (
	"bufio"
	"errors"
	"fmt"
	"os"
	"strings"
	"sync"
	"time"
)

type BEDFile struct {
	Lines    []string
	FileName string
}

func (bedFile *BEDFile) ReadLines(waitGroup *sync.WaitGroup) error {
	defer waitGroup.Done()
	defer fmt.Println("[goroutine] ReadLines done")

	if bedFile.FileName == "" {
		return errors.New("no .bed file specified")
	}

	fmt.Println("[goroutine] ReadLines starting")
	fmt.Print("\n")

	bedFile.Lines = []string{}

	file, err := os.Open(bedFile.FileName)
	check(err)

	start := time.Now()
	fileScanner := bufio.NewScanner(file)
	fileScanner.Split(bufio.ScanLines)
	for fileScanner.Scan() {
		parsedLine := strings.Split(fileScanner.Text(), "\t")
		name := parsedLine[0]
		start := parsedLine[1]
		end := parsedLine[2]
		bedFile.Lines = append(bedFile.Lines, name+"\t"+start+"\t"+end)
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
