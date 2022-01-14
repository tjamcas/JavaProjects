import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

/**
 * Write a description of BabyBirths here.
 * 
 * 1. Modify the method totalBirths (shown in the video for this project) to also print the number of girls names, 
 * the number of boys names and the total names in the file.
 * 
 * 2. Write the method named getRank that has three parameters: an integer named year, a string named name, and a 
 * string named gender (F for female and M for male). This method returns the rank of the name in the file for 
 * the given gender,  where rank 1 is the name with the largest number of births. If the name is not in the file, 
 * then -1 is returned.
 * 
 * 3. Write the method named getName that has three parameters: an integer named year, an integer named rank, and a 
 * string named gender (F for female and M for male). This method returns the name of the person in the file at 
 * this rank, for the given gender, where rank 1 is the name with the largest number of births. If the rank does 
 * not exist in the file, then “NO NAME”  is returned.
 * 
 * 4. What would your name be if you were born in a different year? Write the void method named whatIsNameInYear that 
 * has four parameters: a string name, an integer named year representing the year that name was born,  an integer 
 * named newYear and a string named gender (F for female and M for male). This method determines what name would 
 * have been named if they were born in a different year, based on the same popularity. That is, you should 
 * determine the rank of name in the year they were born, and then print the name born in newYear that is at the 
 * same rank and same gender.
 * 
 * 5. Write the method yearOfHighestRank that has two parameters: a string name, and a string named gender (F for 
 * female and M for male). This method selects a range of files to process and returns an integer, the year with 
 * the highest rank for the name and gender. If the name and gender are not in any of the selected files, it 
 * should return -1.
 * 
 * 6. Write the method getAverageRank that has two parameters: a string name, and a string named gender (F for female 
 * and M for male). This method selects a range of files to process and returns a double representing the average 
 * rank of the name and gender over the selected files. It should return -1.0 if the name is not ranked in any of 
 * the selected files.
 * 
 * 7. Write the method getTotalBirthsRankedHigher that has three parameters: an integer named year, a string named 
 * name, and a string named gender (F for female and M for male). This method returns an integer, the total 
 * number of births of those names with the same gender and same year who are ranked higher than name.
 * 
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BabyBirths {
    public void totalBirths(FileResource fr) {
        /*
         * 1. Modify the method totalBirths (shown in the video for this project) to also print the number of girls 
         * names, the number of boys names and the total names in the file.
         */
        
        int totalBirths = 0;
        int totalGirls = 0;
        int totalGirlsNames = 0;
        int totalBoys = 0;
        int totalBoysNames = 0;
        
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
                totalBoysNames += 1;
            }
            else {
                totalGirls += numBorn;
                totalGirlsNames += 1;
            }
        }
        System.out.println("Total births = " + totalBirths);
        System.out.println("Total girls = " + totalGirls + " / " + "Total girls names = " + totalGirlsNames);
        System.out.println("Total boys = " + totalBoys + " / " + "Total boys names = " + totalBoysNames);
    }
    
    public void testTotalBirths () {
        FileResource fr = new FileResource();
        totalBirths(fr);
    }
    
    public Integer getRank(Integer year, String name, String gender) {
        /*
         * 2. Write the method named getRank that has three parameters: an integer named year, a string named name, 
         * and a string named gender (F for female and M for male). This method returns the rank of the name in the 
         * file for the given gender,  where rank 1 is the name with the largest number of births. If the name is 
         * not in the file, then -1 is returned.
         */
        
        int rank = 0;
        boolean nameFound = false;
        String yearStr = Integer.toString(year);
        // if using shortened test files and directory: String pathName = "us_babynames/us_babynames_test/";
        // if using shortened test files: String fileName = "yob" + yearStr+ "short.csv";
        //System.out.println("Full file name: " + pathName + fileName);
        String pathName = "us_babynames/us_babynames_by_year/";
        String fileName = "yob" + yearStr+ ".csv";
        FileResource fr = new FileResource(pathName + fileName);
        
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                rank += 1;
                if (rec.get(0).equals(name)){
                    nameFound = true;
                    break;
                }
            }
        }
        
        if (nameFound) {
            return rank;
        }
        else {
            return rank = -1;
        }
    }
    
    public void testGetRank() {
        int year = 1971;
        String name = "Frank";
        String gender = "M";
        int rank = getRank(year, name, gender);
        System.out.println(name + "'s rank in " + year + " was: " + rank);
        
    }
    
    
    public String getName(int year, int rank, String gender) {
        /*
         * 3. Write the method named getName that has three parameters: an integer named year, an integer named rank, 
         * and a string named gender (F for female and M for male). This method returns the name of the person in 
         * the file at this rank, for the given gender, where rank 1 is the name with the largest number of births. 
         * If the rank does not exist in the file, then “NO NAME”  is returned.
         */
        
        String name = "NO NAME";
        int curRecord = 0;
        String yearStr = Integer.toString(year);
        //String pathName = "us_babynames/us_babynames_test/";
        String pathName = "us_babynames/us_babynames_by_year/";
        String fileName = "yob" + yearStr + ".csv";
        FileResource fr = new FileResource(pathName + fileName);

        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                curRecord += 1;
                if (curRecord == rank){
                    name = rec.get(0);
                    break;
                }
            }

        }
        return name;
    }
    
    public void testGetName() {
        int year = 1982;
        int rank = 450;
        String gender = "M";
        String name = getName(year, rank, gender);
        System.out.println("In " + year + ", the name at rank " + rank + " was: " + name);
        
    }
    
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender) {
        /*
         * 4.  Write the void method named whatIsNameInYear that has four parameters: a string name, an integer 
         * named year representing the year that name was born,  an integer named newYear and a string named 
         * gender (F for female and M for male). This method determines what name would have been named if they 
         * were born in a different year, based on the same popularity. That is, you should determine the rank 
         * of name in the year they were born, and then print the name born in newYear that is at the same rank 
         * and same gender.
         */
        
        Integer rank = getRank(year, name, gender);
        String newName = getName(newYear, rank, gender);
        
        System.out.println("If " + name + " was born in " + newYear +
                            ", his new name would be: " + 
                            newName);
   
    }
    
    public void testWhatIsNameInYear() {
        //String name = "Mason";
        // should return Liam
        // String name = "Olivia";
        // should return Isabella
        // int year = 2012;
        // int newYear = 2014;
        String name = "Owen";
        int year = 1974;
        int newYear = 2014;
        String gender = "M";
        whatIsNameInYear(name, year, newYear, gender);
        
    }
    
    
    public int yearOfHighestRank(String name, String gender){
        /*
         * Write the method yearOfHighestRank that has two parameters: a string name, and a string named gender (F 
         * for female and M for male). This method selects a range of files to process and returns an integer, the 
         * year with the highest rank for the name and gender. If the name and gender are not in any of the 
         * selected files, it should return -1.
         */
        
        
        Integer highestRankSoFar = null;
        Integer yearOfHighestRank = -1;
        DirectoryResource dr = new DirectoryResource();
        
        for (File f : dr.selectedFiles()) {
            // extract currentYear (int) from name of file
            String fileName = f.getName();
            String yearStr = fileName.substring(3,7);
            //System.out.println(yearStr);
            int currentYear = Integer.parseInt(yearStr);
            // get currentRank of name from file (call getRank()) 
            int currentRank = getRank(currentYear, name, gender);
            // if currentRank = -1 and highestRankSoFar is null, then highestRankSoFar = currentRank
            // else if currentRank higher than highestRankSoFar then yearOfHighestRank = currentYear

            if (currentRank != -1 && highestRankSoFar == null){
                highestRankSoFar = currentRank;
                yearOfHighestRank = currentYear;
            }
            else if(currentRank != -1 && currentRank < highestRankSoFar) {
                highestRankSoFar = currentRank;
                yearOfHighestRank = currentYear;
            }
            
        }
        
        return yearOfHighestRank;
    }
    
    public void testYearofHighestRank(){
        String name = "Mich";
        String gender = "M";
        System.out.println(yearOfHighestRank(name, gender));
    }
    
    
    public double getAverageRank(String name, String gender){
        /*
         * 6. Write the method getAverageRank that has two parameters: a string name, and a string named gender (F 
         * for female and M for male). This method selects a range of files to process and returns a double 
         * representing the average rank of the name and gender over the selected files. It should return -1.0 
         * if the name is not ranked in any of the selected files.
         */
        double averageRank = -1.0;
        int sumRank = 0;
        int count = 0;
        DirectoryResource dr = new DirectoryResource();
        
        for (File f : dr.selectedFiles()){
            //extract currentYear from filename
            String fileName = f.getName();
            String yearStr = fileName.substring(3,7);
            int currentYear = Integer.parseInt(yearStr);
            //currentRank = getRank(Integer year, String name, String gender)
            int currentRank = getRank(currentYear, name, gender);
            //if currentRank != -1, 1. accumulate rank (sumRank += currentRank) 2. increment count
            if (currentRank != -1){
                sumRank += currentRank;
                count += 1;
                //System.out.println(currentRank + "   " + sumRank + "   " + count);
            }
        }
        
        if (sumRank == 0){
            return averageRank;
        }
        else{
            averageRank = (double) sumRank / (double) count;
        }
        return averageRank;
    }
    
    public void testGetAverageRank(){
        String name = "Robert";
        String gender = "M";
        System.out.println("The average rank over the the years selected was: ");
        System.out.println(getAverageRank(name, gender));
    }
    
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender){
        /*
         * 7. Write the method getTotalBirthsRankedHigher that has three parameters: an integer named year, a 
         * string named name, and a string named gender (F for female and M for male). This method returns an 
         * integer, the total number of births of those names with the same gender and same year who are ranked 
         * higher than name. For example, if getTotalBirthsRankedHigher accesses the "yob2012short.csv" file 
         * with name set to “Ethan”, gender set to “M”, and year set to 2012, then this method should return 15, 
         * since Jacob has 8 births and Mason has 7 births, and those are the only two ranked higher than Ethan.
         */
        
        int numBirthsHigher = 0;
        boolean nameFound = false;
        
        //Get rank of name in year
        // int rank = getRank(year, name, gender);
   
        String yearStr = Integer.toString(year);
        // if using shortened test files and directory: String pathName = "us_babynames/us_babynames_test/";
        // if using shortened test files: String fileName = "yob" + yearStr+ "short.csv";
        //System.out.println("Full file name: " + pathName + fileName);

        //Open "year" file
        String pathName = "us_babynames/us_babynames_by_year/";
        String fileName = "yob" + yearStr+ ".csv";
        //String pathName = "us_babynames/us_babynames_test/";
        //String fileName = "yob" + yearStr+ "short.csv";

        FileResource fr = new FileResource(pathName + fileName);

        //Make parseable in for loop             
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {  //Find beginning of gender
                if (rec.get(0).equals(name)){  //Check if currentName equals the searchName
                    nameFound = true;
                    break;
                }
                else{
                    int curNumBirths = Integer.parseInt(rec.get(2));
                    numBirthsHigher += curNumBirths; //Haven't found search name, so add name number of births to accumulator
                }
            }
        }
        
        if (nameFound) {
            return numBirthsHigher;
        }
        else {
            return numBirthsHigher = -1;
        }
                
    }
    
    public void testGetTotalBirthsRankedHigher() {
        int year = 1990;
        String name = "Drew";
        String gender = "M";
        int numBirths = getTotalBirthsRankedHigher(year, name, gender);
        System.out.println("The total number of births with names ranked higher: " + numBirths);
        
    }

}
