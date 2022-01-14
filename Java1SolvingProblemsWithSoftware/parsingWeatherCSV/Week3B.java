
/**
 * Write a description of Week3B here.
 * 
 * 1. Write a method named coldestHourInFile that has one parameter, a CSVParser named parser. This method returns 
 * the CSVRecord with the coldest temperature in the file and thus all the information about the coldest 
 * temperature, such as the hour of the coldest temperature. You should also write a void method named 
 * testColdestHourInFile() to test this method and print out information about that coldest temperature, such as 
 * the time of its occurrence. NOTE: Sometimes there was not a valid reading at a specific hour, so the temperature 
 * field says -9999. You should ignore these bogus temperature values when calculating the lowest temperature.
 * 
 * 2. Write the method fileWithColdestTemperature that has no parameters. This method should return a string that 
 * is the name of the file from selected files that has the coldest temperature. You should also write a void 
 * method named testFileWithColdestTemperature() to test this method. Note that after determining the filename, 
 * you could call the method coldestHourInFile to determine the coldest temperature on that day.
 * 
 * 3. Write a method named lowestHumidityInFile that has one parameter, a CSVParser named parser. This method 
 * returns the CSVRecord that has the lowest humidity. If there is a tie, then return the first such record that 
 * was found.
 * Note that sometimes there is not a number in the Humidity column but instead there is the string “N/A”. This 
 * only happens very rarely. You should check to make sure the value you get is not “N/A” before converting it 
 * to a number. 
 * Also note that the header for the time is either TimeEST or TimeEDT, depending on the time of year. You will 
 * instead use the DateUTC field at the right end of the data file to  get both the date and time of a temperature 
 * reading.
 * You should also write a void method named testLowestHumidityInFile() to test this method.
 * 
 * 4. Write the method lowestHumidityInManyFiles that has no parameters. This method returns a CSVRecord that has 
 * the lowest humidity over all the files. If there is a tie, then return the first such record that was found. 
 * You should also write a void method named testLowestHumidityInManyFiles() to test this method and to print 
 * the lowest humidity AND the time the lowest humidity occurred. Be sure to test this method on two files so you 
 * can check if it is working correctly. 
 * 
 * 5. Write the method averageTemperatureInFile that has one parameter, a CSVParser named parser. This method 
 * returns a double that represents the average temperature in the file. You should also write a void method named 
 * testAverageTemperatureInFile() to test this method. 
 * 
 * 6. Write the method averageTemperatureWithHighHumidityInFile that has two parameters, a CSVParser named parser 
 * and an integer named value. This method returns a double that represents the average temperature of only those 
 * temperatures when the humidity was greater than or equal to value. You should also write a void method named 
 * testAverageTemperatureWithHighHumidityInFile() to test this method. 
 * 
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class Week3B {
    
    public CSVRecord coldestHourInFile(CSVParser parser) {
        /*
         * 1. Write a method named coldestHourInFile that has one parameter, a CSVParser named parser. This method returns 
         * the CSVRecord with the coldest temperature in the file and thus all the information about the coldest 
         * temperature, such as the hour of the coldest temperature. You should also write a void method named 
         * testColdestHourInFile() to test this method and print out information about that coldest temperature, such as 
         * the time of its occurrence. NOTE: Sometimes there was not a valid reading at a specific hour, so the temperature 
         * field says -9999. You should ignore these bogus temperature values when calculating the lowest temperature.
         * 
         * String conversion:
         * String yearStr = row.get("Year");
         * int year = Integer.parseInt(yearStr);
         * 
         * String tempStr =row.get("Temperature");
         * Double temp = Double.parseDouble(tempStr);
         */
        CSVRecord smallestSoFar = null;
        
        for (CSVRecord currentRow : parser) {
            if (smallestSoFar == null) {
                smallestSoFar = currentRow;          
                }
            else {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double lowestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));
                /*
                * or in the case where the first row of the parser has TempF = -9999
                * if ((currentTemp < lowestTemp && currentTemp != -9999) || (lowestTemp == -9999)) {}
                */
                //if (currentTemp < lowestTemp && currentTemp != -9999) {
                if ((currentTemp < lowestTemp && currentTemp != -9999) || (lowestTemp == -9999)) {
                    smallestSoFar = currentRow;
                    }
            }
        }
        return smallestSoFar;
    }
    
    public void testColdestHourInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord coldestHour = coldestHourInFile(parser);
        System.out.println(coldestHour);
    }
    
    
    public String fileWithColdestTemperature() {
        /*
         * Write the method fileWithColdestTemperature that has no parameters. This method should return a string that 
         * is the name of the file from selected files that has the coldest temperature. You should also write a void 
         * method named testFileWithColdestTemperature() to test this method. Note that after determining the filename, 
         * you could call the method coldestHourInFile to determine the coldest temperature on that day.
         */
        
        String coldFile = "";
        CSVRecord coldestTempOverall = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            String fileName = f.getName();
            //String fileWithPath = f.getCanonicalPath();
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            CSVRecord currentColdestHour = coldestHourInFile(parser);
            if (coldestTempOverall == null) {
                coldestTempOverall = currentColdestHour;
                coldFile = fileName;
                //coldFile = fileWithPath;
            }
            else {
                double fileColdest = Double.parseDouble(currentColdestHour.get("TemperatureF"));
                double overallColdest = Double.parseDouble(coldestTempOverall.get("TemperatureF"));
                if (fileColdest < overallColdest) {
                    coldestTempOverall = currentColdestHour;
                    coldFile = fileName;
                    //coldFile = fileWithPath;
                    }
             }
        }
        return coldFile;
    }
    
    public void testFileWithColdestTemperature() {        
        String fileName = fileWithColdestTemperature();
        String filePath = "nc_weather/2013/";
        String fullFileName = filePath + fileName;
        System.out.println("The coldest temp can be found here: " + fullFileName);
        FileResource fr = new FileResource(fullFileName);
        CSVParser parser = fr.getCSVParser();
        CSVRecord coldestHour = coldestHourInFile(parser);
        String coldestTemp = coldestHour.get("TemperatureF");
        System.out.println("The coldest temperature (F) on that day was " + coldestTemp);
        System.out.println("All the temperatures on the coldest day were:");
        parser = fr.getCSVParser();
        for (CSVRecord row : parser) {
            String timestamp = row.get("DateUTC");
            String temp = row.get("TemperatureF");
            System.out.println(timestamp + ":   " + temp);
        }
        
    }
    
    
    
    
    public CSVRecord lowestHumidityInFile(CSVParser parser){
        /*
         * Write a method named lowestHumidityInFile that has one parameter, a CSVParser named parser. This method 
         * returns the CSVRecord that has the lowest humidity. If there is a tie, then return the first such record that 
         * was found.
         * Note that sometimes there is not a number in the Humidity column but instead there is the string “N/A”. This 
         * only happens very rarely. You should check to make sure the value you get is not “N/A” before converting it 
         * to a number. 
         * Also note that the header for the time is either TimeEST or TimeEDT, depending on the time of year. You will 
         * instead use the DateUTC field at the right end of the data file to  get both the date and time of a temperature 
         * reading.
         * You should also write a void method named testLowestHumidityInFile() to test this method.
         * 
         */
        
        CSVRecord lowestSoFar = null;
        
        for (CSVRecord currentRow : parser) {
            //System.out.println(currentRow.get("TimeEDT") + "   " + currentRow.get("Humidity"));
            if (lowestSoFar == null) {
                lowestSoFar = currentRow;
                }
            else if (currentRow.get("Humidity").equals("N/A")) {
                continue;
                }
            else {
                double currentHumidity = Double.parseDouble(currentRow.get("Humidity"));
                double lowestHumidity = Double.parseDouble(lowestSoFar.get("Humidity"));
                if (currentHumidity < lowestHumidity) {
                    lowestSoFar = currentRow;
                    }
                }
           }
        return lowestSoFar;    
        }
        
    
        public void testLowestHumidityInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord lowestHumidity = lowestHumidityInFile(parser);
        System.out.println("Lowest Humidity: " + lowestHumidity.get("Humidity") +
                            "%  at time: " + lowestHumidity.get("DateUTC"));
    }
    
    public CSVRecord lowestHumidityInManyFiles() {
        /*
         * Write the method lowestHumidityInManyFiles that has no parameters. This method returns a CSVRecord that has 
         * the lowest humidity over all the files. If there is a tie, then return the first such record that was found. 
         * You should also write a void method named testLowestHumidityInManyFiles() to test this method and to print 
         * the lowest humidity AND the time the lowest humidity occurred. Be sure to test this method on two files so you 
         * can check if it is working correctly. 
         */
        //String foundFile = "";
        CSVRecord lowestHumidityOverall = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            //String fileName = f.getName();
            //System.out.println(f.getName());
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            CSVRecord currentLowestHour = lowestHumidityInFile(parser);
            if (lowestHumidityOverall == null) {
                lowestHumidityOverall = currentLowestHour;
                //foundFile = fileName;
            }
            else if (currentLowestHour.get("Humidity") == "N/A") {
                continue;
            }
            else {
                double fileLowest = Double.parseDouble(currentLowestHour.get("Humidity"));
                double overallLowest = Double.parseDouble(lowestHumidityOverall.get("Humidity"));
                if (fileLowest < overallLowest) {
                    lowestHumidityOverall = currentLowestHour;
                    //foundFile = fileName;
                    }
             }
        }
        return lowestHumidityOverall;
    }
    
    public void testLowestHumidityInManyFiles() {
        CSVRecord lowestHumidityHour = lowestHumidityInManyFiles();
        String humidity = lowestHumidityHour.get("Humidity");
        String timestamp = lowestHumidityHour.get("DateUTC");
        System.out.println("Lowest humidity was " + humidity + "% at " + timestamp);
    }

    public Double averageTemperatureInFile(CSVParser parser) {
        /*
         * 5. Write the method averageTemperatureInFile that has one parameter, a CSVParser named parser. This method 
         * returns a double that represents the average temperature in the file. You should also write a void method 
         * named testAverageTemperatureInFile() to test this method. 
         */
        
        double result = 0.0;
        int count = 0;
        double sum = 0.0;
        
        for (CSVRecord row : parser) {
            String tempStr = row.get("TemperatureF");
            if (!tempStr.equals("-9999")) {
                double temp = Double.parseDouble(tempStr);
                sum = sum + temp;
                count = count + 1;
                //System.out.println(sum + "   " + count);
            }
        }
        result = sum / count;
        return result;
    }
    
    public void testAverageTemperatureInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double fileAvgTemp = averageTemperatureInFile(parser);
        System.out.println("Average temperature in file is: " + fileAvgTemp);
    }
    
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, Integer value) {
        /*
         * 6. Write the method averageTemperatureWithHighHumidityInFile that has two parameters, a CSVParser named parser 
         * and an integer named value. This method returns a double that represents the average temperature of only those 
         * temperatures when the humidity was greater than or equal to value. You should also write a void method named 
         * testAverageTemperatureWithHighHumidityInFile() to test this method. 
         * 
         */
        double result = 0.0;
        double tempSum = 0.0;
        int count = 0;
        
        for (CSVRecord row : parser) {
            String tempStr = row.get("TemperatureF");
            int humidity = Integer.parseInt(row.get("Humidity"));
            if ((!tempStr.equals("-9999")) && (humidity>= value)) {
                double temp = Double.parseDouble(tempStr);
                tempSum = tempSum + temp;
                count = count + 1;
            }
        }
        if (count == 0) {
            result = -9999.99;
        }
        else {
            result = tempSum / count;
        }
        
        return result;
    }
    
    public void testAverageTemperatureWithHighHumidityInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        int humidity = 80;
        double avgTempAboveHumidity = averageTemperatureWithHighHumidityInFile (parser, humidity);
        if (avgTempAboveHumidity == -9999.99) {
            System.out.println("No temperatures at or above that humidity value");
        }
        else{
            System.out.println("Average temperature when high Humidity is: " + avgTempAboveHumidity);
        }
    }
}
