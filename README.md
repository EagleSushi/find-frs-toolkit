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

### findfrs.combinations.regions.presence.jar
> Similar to findfrs.combinations.regions.count.jar, this looks for the different prefixes in different genome regions.
> Instead of counting the groupings, this simply identifies the presence of a prefix within a given location, using a 1 or a 0, 1 being present and 0 being absent.

### findfrs.combinations.regionvariants.count.jar & findfrs.combinations.regionvariants.presence.jar
> These two are similar their counterparts with just region in its name, except it looks for prefixes in each region and subregion (i.e fr0:1, fr0:2)

### findfrs.patterns.jar
> This program searches for any genomes across regions that contain duplicate names AND duplicate start locations AND duplicate end locations.

### findfrs.regioncounts.jar
> This program exports 3 different csv files.
> One csv file reveals the different regions present in the .bed file and their count
> Another csv file reveals the variants (subregions) present in the .bed file
> The last csv file reveals the count of the different variants (subregions) in the .bed file

### findfrs.ranges.jar
> This searches the given directory for .gff3 files, which are annotations for a bed file
> This then concatenates the .bed file line and the line in the .gff3 file together in one line of the .csv output, if the start and end location are within any range in the .gff3 file
