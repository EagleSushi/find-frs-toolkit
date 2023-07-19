# Find FRs Toolkit
---

## Description

Find FRs Toolkit is a combination of programs used to analyze .bed files. All of the programs output to a .csv file format.

## Prerequisties 

- Java to run .jar files
- Go to run .exe/.linux64 files <br />
[Java installation](https://www.java.com/en/download/help/download_options.html) <br />
[Go installation](https://go.dev/doc/install) <br />

## Usage

1. Download/clone repository
2. Navigate to the \out\ directory
3. Execute ```$ java -jar <desired .jar file>``` or ```$ ./<desired .exe/.linux64 file>```
4. Follow the prompts

## Sample Data

The sample outputs are linked in each of the respective title for each program description.

[Sample data used](https://github.com/vfdizon/find-frs-toolkit/tree/master/sample/data)

---

### [findfrs.combinations.regions.count.jar (link to output)](https://github.com/vfdizon/find-frs-toolkit/blob/master/sample/output/findfrs.combinations.regions.count.csv)
> This program counts the different prefixes found in names of genomes in different genome regions
> There can be multiple prefixes to search for each group, and any prefixes that haven't been specified will be put in an "other" group.

Example usage: 
```
$ java -jar findfrs.combinations.regions.count.jar
Please input the bed file directory:
..\sample\data\sampleData.bed
Please input the output desired .csv file directory:
..\sample\output\findfrs.combinations.regions.count.csv
How many groups are you looking for? (Not including the "Other" group)
1
Please input the prefix of group 1 (case sensitive seperated by semicolons):
medtr
Wrote to CSV: ..\sample\output\findfrs.combinations.regions.count.csv
```
---
### [findfrs.combinations.regions.presence.jar (link to output)](https://github.com/vfdizon/find-frs-toolkit/blob/master/sample/output/findfrs.combinations.regions.presence.csv)
> Similar to findfrs.combinations.regions.count.jar, this looks for the different prefixes in different genome regions.
> Instead of counting the groupings, this simply identifies the presence of a prefix within a given location, using a 1 or a 0, 1 being present and 0 being absent.

Example usage: 
```
$ java -jar findfrs.combinations.regions.presence.jar
Please input the bed file directory:
..\sample\data\sampleData.bed
Please input the output desired .csv file directory:
..\sample\output\findfrs.combinations.presence.count.csv
How many groups are you looking for? (Not including the "Other" group)
1
Please input the prefix of group 1 (case sensitive seperated by semicolons):
medtr
Wrote to CSV: ..\sample\output\findfrs.combinations.regions.presence.csv
Time elapsed: 558ms
```
---
### [findfrs.combinations.regionvariants.count.jar (link to output)](https://github.com/vfdizon/find-frs-toolkit/blob/master/sample/output/findfrs.combinations.regionvariants.count.csv) & [findfrs.combinations.regionvariants.presence.jar link to output)](https://github.com/vfdizon/find-frs-toolkit/blob/master/sample/output/findfrs.combinations.regionvariants.presence.csv)
> These two are similar their counterparts with just region in its name, except it looks for prefixes in each region and subregion (i.e fr0:1, fr0:2)

Example usage:
```
$ java -jar findfrs.combinations.regionvariants.count.jar
Please input the bed file directory:
..\sample\data\sampleData.bed
Please input the output desired .csv file directory:
..\sample\output\findfrs.combinations.regionvariants.count.csv
How many groups are you looking for? (Not including the "Other" group)
1
Please input the prefix of group 1 (case sensitive seperated by semicolons):
medtr
Wrote to CSV: ..\sample\output\findfrs.combinations.regionvariants.count.csv
Time elapsed: 558ms
```

```
$ java -jar findfrs.combinations.regionvariants.presence.jar
Please input the bed file directory:
..\sample\data\sampleData.bed
Please input the output desired .csv file directory:
..\sample\output\findfrs.combinations.regionvariants.presence.csv
How many groups are you looking for? (Not including the "Other" group)
1
Please input the prefix of group 1 (case sensitive seperated by semicolons):
medtr
Wrote to CSV: ..\sample\output\findfrs.combinations.regionvariants.presence.csv
Time elapsed: 558ms
```
---
### [findfrs.patterns.jar (link to output)](https://github.com/vfdizon/find-frs-toolkit/blob/master/sample/output/findfrs.patterns.csv)
> This program searches for any genomes across regions that contain duplicate names AND duplicate start locations AND duplicate end locations.

Example usage
```
$ java -jar findfrs.patterns.jar
Please enter the directory of the BED file
..\sample\data\sampleData.bed
Please enter the desired directory of the output CSV file
..\sample\output\findfrs.patterns.csv
Duplicate found
1
Duplicate found
2
Duplicate found
3
Duplicate found
3
Done in 388ms
Wrote to CSV: ..\sample\output\findfrs.patterns.csv
Parsed BED file in 392ms
```

---
### findfrs.regioncounts.jar [Link to Output 1](https://github.com/vfdizon/find-frs-toolkit/blob/master/sample/output/findfrs.regioncounts_regionCounts.csv), [Link to Output 2](https://github.com/vfdizon/find-frs-toolkit/blob/master/sample/output/findfrs.regioncounts_regionVariants.csv), [Link to Output 3](https://github.com/vfdizon/find-frs-toolkit/blob/master/sample/output/findfrs.regioncounts_regionVariantCounts.csv) 
> This program exports 3 different csv files.
> One csv file reveals the different regions present in the .bed file and their count
> Another csv file reveals the variants (subregions) present in the .bed file
> The last csv file reveals the count of the different variants (subregions) in the .bed file

Example usage: 
```
$ java -jar findfrs.regioncounts.jar
Please enter the directory of the BED file:
..\sample\data\sampleData.bed
Please enter the directory of the CSV file: (Doesn't have to end with .csv)
..\sample\output\findfrs.regioncounts
BEDFile: ..\sample\data\sampleData.bed
Finished Parsing BED File in: 433ms
Wrote to CSV: ..\sample\output\findfrs.regioncounts_regionCounts.csv
Wrote to CSV: ..\sample\output\findfrs.regioncounts_regionVariants.csv
Wrote to CSV: ..\sample\output\findfrs.regioncounts_regionVariantCounts.csv
Time taken: 497ms
```

### [findfrs.ranges.jar & findfrs.ranges.exe (Windows) & findfrs.ranges.linux64 (Linux 64) (link to output)](https://github.com/vfdizon/find-frs-toolkit/blob/master/sample/output/findfrs.ranges.csv)
> This searches the given directory for .gff3 files, which are annotations for a bed file
> This then concatenates the .bed file line and the line in the .gff3 file together in one line of the .csv output, if the start and end location are within any range in the .gff3 file
> The .jar file is written in java, and the .exe/./linux64 executables are written in golang.
> The java version is better suited for smaller datasets, while the golang version is better for larger datasets, and utilizes concurrency to search the .bed file for each .gff3 file.
> The golang version also writes to the .csv file in batches as the program is running.

Example usage (.jar):
```
$ java -jar findfrs.ranges.jar
Please enter .bed file path:
..\sample\data\sampleData.bed
Please enter the directory of all of the annotations (.gff3) files
..\sample\data
Please enter the output directory for the CSV file
..\sample\output\findfrs.ranges.csv
Analyzing .bed file...
Annotation found: medtr.HM004
Finished parsing annotation file: medtr.HM004 in: 1893ms
Annotation found: medtr.HM010
Finished parsing annotation file: medtr.HM010 in: 1559ms
Finished parsing annotation files in: 3464ms
Finished analyzing .bed file in: 2390ms
Wrote to CSV: ..\sample\output\findfrs.ranges.csv
Finished in 5901 milliseconds.
```
Example usage (.exe/linux64):
```
$ .\findfrs.ranges.exe
Please enter the .BED file path:
..\sample\data\sampleData.bed
Please enter the directory path for the .gff3 files:
..\sample\data
Please enter the output file path: ( ending in .csv)
..\sample\output\findfrs.ranges.csv
[goroutine] ReadLines starting

[goroutine] AddAllAnnotations starting

Read BED File in 35.3038ms
[goroutine] ReadLines done
Read Annotation File: ..\sample\data\medtr.HM004.gnm1.ann1.2XTB.gene_models_main.gff3 in 488.7441ms
Read Annotation File: ..\sample\data\medtr.HM010.gnm1.ann1.WV9J.gene_models_main.gff3 in 496.9368ms
[goroutine] AddAllAnnotations done
Created..\sample\output\findfrs.ranges.csv

Writing to ..\sample\output\findfrs.ranges.csv concurrently with a batch size of 1000000 lines each

[goroutine] checkingForBEDfile entries for medtr.HM010
[goroutine] checkingForBEDfile entries for medtr.HM004
[goroutine] checkingForBEDfile done for medtr.HM004 in 54.0884893s
[goroutine] checkingForBEDfile done for medtr.HM010 in 1m7.9875196s
Analyzed in 1m7.9943434s
Finished analysis, flushing remaining batch lines
Wrote batch 1 to file
Closed ..\sample\output\findfrs.ranges.csv
```

---

