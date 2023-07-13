# Find FRs Toolkit
---

## Description

Find FRs Toolkit is a combination of java programs used to analyze .bed files. All of the programs output to a .csv file format. 

## Usage

1. Download/clone repository
2. Navigate to the \out\ directory
3. Execute ```$ java -jar <desired .jar file>```
4. Follow the prompts

---

### findfrs.combinations.regions.count.jar
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
### findfrs.combinations.regions.presence.jar
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
### findfrs.combinations.regionvariants.count.jar & findfrs.combinations.regionvariants.presence.jar
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
### findfrs.patterns.jar
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
### findfrs.regioncounts.jar
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

### findfrs.ranges.jar
> This searches the given directory for .gff3 files, which are annotations for a bed file
> This then concatenates the .bed file line and the line in the .gff3 file together in one line of the .csv output, if the start and end location are within any range in the .gff3 file

Example usage:
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
