
/**
 * Write a description of Week3A here.
 * 
 * 1. Write a method named tester that will create your CSVParser and call each of the methods below in 
 * parts 2, 3, 4, and 5. 
 * 
 * 2. Write a method named countryInfo that has two parameters, parser is a CSVParser and country is a String. 
 * This method returns a string of information about the country or returns “NOT FOUND” if there is no 
 * information about the country. The format of the string returned is the country, followed by “: “, followed 
 * by a list of the countries’ exports, followed by “: “, followed by the countries export value.
 * 
 * 3. Write a void method named listExportersTwoProducts that has three parameters, parser is a CSVParser, 
 * exportItem1 is a String and exportItem2 is a String. This method prints the names of all the countries that 
 * have both exportItem1 and exportItem2 as export items.
 * 
 * 4. Write a method named numberOfExporters, which has two parameters, parser is a CSVParser, and exportItem 
 * is a String. This method returns the number of countries that export exportItem. For example, using the 
 * file exports_small.csv, this method called with the item “gold” would return 3.
 * 
 * 5. Write a void method named bigExporters that has two parameters, parser is a CSVParser, and amount is a 
 * String in the format of a dollar sign, followed by an integer number with a comma separator every three 
 * digits from the right. An example of such a string might be “$400,000,000”. This method prints the names 
 * of countries and their Value amount for all countries whose Value (dollars) string is longer than the 
 * amount string. You do not need to parse either string value as an integer, just compare the lengths of the 
 * strings.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import org.apache.commons.csv.*;

public class Week3A {
    
    public void tester() {
        /*FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        String peruResult = countryInfo(parser, "Peru");
        System.out.println(peruResult);*/
        
        /*FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        String USAResult = countryInfo(parser, "Nauru");
        System.out.println(USAResult);*/
        
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        listExportersTwoProducts(parser, "cotton", "flowers");
        
        fr = new FileResource();
        parser = fr.getCSVParser();
        int count = numberOfExporters(parser, "cocoa");
        System.out.println(count);
        
        fr = new FileResource();
        parser = fr.getCSVParser();
        bigExporters(parser, "$999,999,999,999");

    }
    
    /*
     * Write a method named countryInfo that has two parameters, parser is a CSVParser and country is a String. 
     * This method returns a string of information about the country or returns “NOT FOUND” if there is no information 
     * about the country. The format of the string returned is the country, followed by “: “, followed by a list of 
     * the countries’ exports, followed by “: “, followed by the countries export value.
     */
    
    public String countryInfo(CSVParser parser, String country){
        String result = "NOT FOUND";
        for (CSVRecord record : parser) {
            String recordCountry = record.get("Country");
            // could have used below if conditional:
            // if (recordCountry.indexOf(country) != -1)
            if (recordCountry.equals(country)) {
                String export = record.get("Exports");
                String value = record.get("Value (dollars)");
                result = country + ": " + export + ": " + value;
                return result;
            }
            
        }
        
        return result;
    }
    
    public void listExportersTwoProducts(CSVParser parser, String exportitem1, String exportitem2){
        /*
         * Write a void method named listExportersTwoProducts that has three parameters, parser is a CSVParser, 
         * exportItem1 is a String and exportItem2 is a String. This method prints the names of all the countries 
         * that have both exportItem1 and exportItem2 as export items. For example, using the file 
         * exports_small.csv, this method called with the items “gold” and “diamonds” would print the countries
         * "Namibia" and "South Africa".
         */
        
        for (CSVRecord record : parser) {
            String exports = record.get("Exports");
            if (exports.contains(exportitem1) && exports.contains(exportitem2)) {
                System.out.println(record.get("Country"));
                
            }
        }
        
    }

    
    public int numberOfExporters(CSVParser parser, String exportitem) {
        /*
         * Write a method named numberOfExporters, which has two parameters, parser is a CSVParser, and exportItem is a 
         * String. This method returns the number of countries that export exportItem. For example, using the file 
         * exports_small.csv, this method called with the item “gold” would return 3.
         */
        
        int result = 0;
        
        for (CSVRecord record : parser) {
            String exports = record.get("Exports");
            if (exports.contains(exportitem)) {
                result +=1;
            }
        }
        
        return result;
    }
    
    public void bigExporters(CSVParser parser, String amount) {
        /*
         * Write a void method named bigExporters that has two parameters, parser is a CSVParser, and amount is a String 
         * in the format of a dollar sign, followed by an integer number with a comma separator every three digits from 
         * the right. An example of such a string might be “$400,000,000”. This method prints the names of countries 
         * and their Value amount for all countries whose Value (dollars) string is longer than the amount string. You 
         * do not need to parse either string value as an integer, just compare the lengths of the strings.
         */
        
        for (CSVRecord record : parser) {
            String value = record.get("Value (dollars)");
            if (value.length() > amount.length()) {
                System.out.println(record.get("Country") + " " + value);
            }
            
        }
    }
}
