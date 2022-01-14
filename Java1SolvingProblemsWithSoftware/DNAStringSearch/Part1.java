
/**
 * Write a description of Part1 here.
 * Create a new Java Class named Part1. Copy and paste the code from your Part1 class 
 * in your StringsSecondAssignments project into this class.
 *
 * Make a copy of the printAllGenes method called getAllGenes. Instead of printing the 
 * genes found, this method should create and return a StorageResource containing the genes 
 * found. Remember to import the edu.duke libraries otherwise you will get an error message 
 * cannot find the class StorageResource.
 *
 * Make sure you test your getAllGenes method.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
 
public class Part1 {
    
    // Write the method findGene that has one String parameter dna, representing a string of DNA. In 
    // this method you should do the following:
    //   1. Find the index of the first occurrence of the start codon “ATG”. If there is no “ATG”, return the empty string.
    //   2a. Find the index of the first occurrence of the stop codon “TAA” after the first occurrence of 
    //     “ATG” that is a multiple of three away from the “ATG”. Hint: call findStopCodon.
    //   2b. Find the index of the first occurrence of the stop codon “TAG” after the first occurrence of 
    //     “ATG” that is a multiple of three away from the “ATG”. 
    //   2c. Find the index of the first occurrence of the stop codon “TGA” after the first occurrence of 
    //     “ATG” that is a multiple of three away from the “ATG”. 
    //   3. Return the gene formed from the “ATG” and the closest stop codon that is a multiple of three 
    //     away. If there is no valid stop codon and therefore no gene, return the empty string.
       
    public String findGene(String dna) {
        String startCodon = "ATG";
        String stopTAACodon = "TAA";
        String stopTAGCodon = "TAG";
        String stopTGACodon = "TGA";
        Integer stopIndex = 0;
        Integer startIndex = dna.indexOf(startCodon);
        if (startIndex == -1) {
            return "";
        }
        else {
            Integer stopTAAIndex = findStopCodon(dna, startIndex, stopTAACodon);
            Integer stopTAGIndex = findStopCodon(dna, startIndex, stopTAGCodon);
            Integer stopTGAIndex = findStopCodon(dna, startIndex, stopTGACodon);
            Integer temp = Math.min(stopTAAIndex, stopTAGIndex);
            stopIndex = Math.min(temp, stopTGAIndex);
            if (stopIndex == dna.length()) {
                return "";
            }
            return dna.substring(startIndex, stopIndex + 3);
        }
    }

    
    // Write the method findStopCodon that has three parameters, a String parameter named dna, 
    // an integer parameter named startIndex that represents where the first occurrence of ATG occurs 
    // in dna, and a String parameter named stopCodon. This method returns the index of the first 
    // occurrence of stopCodon that appears past startIndex and is a multiple of 3 away from startIndex. 
    // If there is no such stopCodon, this method returns the length of the dna strand.
    
    public Integer findStopCodon(String dna, Integer startIndex, String stopCodon) {
        Integer currIndex = dna.indexOf(stopCodon, startIndex);
        while (currIndex != -1) {
            if ((currIndex - startIndex) % 3 == 0) {
                return currIndex;
            }
            else {
                currIndex = dna.indexOf(stopCodon, currIndex + 1);
            }
        }
        return dna.length();
    }
    

    // Write the void method testFindGene that has no parameters. You should create five DNA strings. The 
    // strings should have specific test cases such as DNA with no “ATG”, DNA with “ATG” and one valid stop 
    // codon, DNA with “ATG” and multiple valid stop codons, DNA with “ATG” and no valid stop codons. Think 
    // carefully about what would be good examples to test. For each DNA string you should: 
    //   1. Print the DNA string. 
    //   2. Calculate the gene by sending this DNA string as an argument to findGene. If a gene exists 
    //        following our algorithm above, then print the gene, otherwise print the empty string.    
    
    public void testFindGene(){
        
        //            0V23456789V12345 - should result in "ATGCGTAATTAG"
        String dna = "AATGCGTAATTAGTCG";
        System.out.println(dna);
        String foundGene = findGene(dna);
        System.out.println("Found gene: " + foundGene);
        
        //     012V45678901234567890123456789 - Should result in ""
        dna = "CGGATGGTTGATAATAAGCCTAAGCTAAA";
        System.out.println(dna);
        foundGene = findGene(dna);
        System.out.println("Found gene: " + foundGene);
        
        //     012V45678901234567890123V56789 - should result in ATGGTTGATAAGCCTAAGCTATAA
        dna = "CGGATGGTTGATAAGCCTAAGCTATAACTATGAAAGTAGCGTGTT";
        System.out.println(dna);
        foundGene = findGene(dna);
        System.out.println("Found gene: " + foundGene);
        
        //     012V45678901234567890123456789 - No ATG - Should result in ""
        dna = "CGGAGTGGTTGATAAGCCTAAGCTAAA";
        System.out.println(dna);
        foundGene = findGene(dna);
        System.out.println("Found gene: " + foundGene);
        
        //     012V45678901234567890123V56789 - should result in ATGGTTGATAAGCCTAAGCTATGA
        dna = "CGGATGGTTGATAAGCCTAAGCTATGACTATGAAAGTAGCGTTAAGTT";
        System.out.println(dna);
        foundGene = findGene(dna);
        System.out.println("Found gene: " + foundGene);
  
    }
    
    // Write the void method testFindStopCodon that calls the method findStopCodon with several
    // examples and prints out the results to check if findStopCodon is working correctly. Think about
    // what types of examples you should check. For example, you may want to check some strings of DNA
    // that have genes and some that do not.
    
    public void testFindStopCodon() {
        String dna = "AATGCGTAATTAATCG"; // should result in 10
        Integer stopCodonIdx = findStopCodon(dna, 1, "TAA");
        System.out.println("Found stop Codon at: " + stopCodonIdx);
         
        //     012V45678901234567890123456789 - No stop codon - Should result in 26
        dna = "CGGATGGTTGATAAGCCTAAGCTAAA";
        stopCodonIdx = findStopCodon(dna, 3, "TAA");
        System.out.println("Found stop Codon at: " + stopCodonIdx);
 
        //     012V45678901234567890123V56789 - should result in 24
        dna = "CGGATGGTTGATAAGCCTAAGCTATAA";
        stopCodonIdx = findStopCodon(dna, 3, "TAA");
        System.out.println("Found stop Codon at: " + stopCodonIdx);
 
        //     012V45678901V34567890123456789 - Should result in 12
        dna = "CGGATGGGTTGATAAGCCTAAGCTAAA";
        stopCodonIdx = findStopCodon(dna, 3, "TAA");
        System.out.println("Found stop Codon at: " + stopCodonIdx);
        
        //     012V45678901234567890123V56789 - should result in 24
        dna = "CGGATGGTTGATAAGCCTAAGCTATAG";
        stopCodonIdx = findStopCodon(dna, 3, "TAG");
        System.out.println("Found stop Codon at: " + stopCodonIdx);
 
        //     012V45678901234567890123V56789 - should result in 24
        dna = "CGGATGGTTGATAAGCCTAAGCTATGA";
        stopCodonIdx = findStopCodon(dna, 3, "TGA");
        System.out.println("Found stop Codon at: " + stopCodonIdx);
 
    }
    
    // Write the void method printAllGenes that has one String parameter dna, representing a string of DNA. 
    // In this method you should repeatedly find genes and print each one until there are no more genes. 
    // Hint: remember you learned a while(true) loop and break.
    
    public void printAllGenes(String dna) {
      String startCodon = "ATG";
      String gene = "";
      Integer startIndex = dna.indexOf(startCodon);
      while( true ){
          gene = findGene(dna.substring(startIndex));
          if (gene != "") {
              System.out.println(gene);
              startIndex = startIndex + gene.length();
              startIndex = dna.indexOf(startCodon, startIndex);
              if (startIndex == -1) {
                  break;
                }
            }
          else {
              break;
            }
        }
    }
    
    public void testPrintAllGenes() {
        //            0V23456789V12345 - should result in "ATGCGTAATTAG"
        String dna = "AATGCGTAATTAGAGTCGAATGCGTAATTAGTGATAATCGCGGATGGTTGATAAGCCTAAGCTATAACTATGAAAGTAGCGTGTTGAAAAGGGATGGTTGATAAGCCTAAGCTATGACTATGAAAGTAGCGTTAAGTT";
        printAllGenes(dna);
    
    }

    
    public Integer howMany(String stringa, String stringb) {
        /**
         * Write the method named howMany that has two String parameters named stringa and stringb. This method 
         * returns an integer indicating how many times stringa appears in stringb, where each occurrence of stringa 
         * must not overlap with another occurrence of it.
         */

        Integer count = 0;
        Integer startIndex = 0;
        Integer newIndex = 0;
        startIndex = stringb.indexOf(stringa);
        while (startIndex != -1) {
            count = count + 1;
            newIndex = startIndex + stringa.length();
            if (newIndex >= stringb.length()) {
                break;
            }
            startIndex = stringb.indexOf(stringa, newIndex);
        }
        return count;      
    }
    
    
    public StorageResource getAllGenes(String dna) {
        
        /**
          * This method creates and returns a StorageResource containing the genes 
          * found. Remember to import the edu.duke libraries otherwise you will get an error message 
          * cannot find the class StorageResource.
          */
         
      String startCodon = "ATG";
      String gene = "";
      StorageResource geneList = new StorageResource();
      Integer startIndex = dna.indexOf(startCodon);
      while( true ){
          gene = findGene(dna.substring(startIndex));
          if (gene != "") {
              //System.out.println(gene);
              geneList.add(gene);
              startIndex = startIndex + gene.length();
              startIndex = dna.indexOf(startCodon, startIndex);
              if (startIndex == -1) {
                  break;
                }
            }
          else {
              break;
            }
        }
        return geneList;
    }
    
    
    public void testGetAllGenes() {
        String dna = "AATGCGTAATTAGAGTCGAATGCGTAATTAGTGATAATCGCGGATGGTTGATAAGCCTAAGCTATAACTATGAAAGTAGCGTGTTGAAAAGGGATGGTTGATAAGCCTAAGCTATGACTATGAAAGTAGCGTTAAGTT";
        StorageResource genes = getAllGenes(dna);
        System.out.println("Test data: " + dna);
        System.out.println("Genes found:");
        for (String gene : genes.data()) {
            System.out.println(gene);
        }
    
        
    }
    
    
    public Double cgRatio(String dna) {
       Double ratio = 0.0;
       Integer cCount = howMany("C", dna);
       //Double cCountDbl = (double)cCount;
       //System.out.println("cCountDbl = " + cCountDbl);
       Integer gCount = howMany("G", dna);
       //Double gCountDbl = (double)gCount;
       //System.out.println("gCountDbl = " + gCountDbl);
       ratio = ((double)cCount + gCount)/dna.length();
       return ratio;
    }
    
    public void testCGRatio() {
        String dna = "AATGCGTAAT";
        Double ratio = cgRatio(dna);
        System.out.println(ratio);
    }
    
    public Integer countCTG(String dna) {
      //Write a method countCTG that has one String parameter dna, and returns the number of 
      //times the codon CTG appears in dna.
      Integer count = howMany("CTG", dna);
      return count;
    }
        
    public void testcountCTG() {
        String dna = "AACTGCGTCTGAATCTG";
        Integer count = countCTG(dna);
        System.out.println(count);
    }
    
    public void processGenes(StorageResource sr) {
        /**
         * This method processes all the strings in sr to find out information about them. 
         * Specifically, it should: 
         * print all the Strings in sr that are longer than 9 characters
         * print the number of Strings in sr that are longer than 9 characters
         * print the Strings in sr whose C-G-ratio is higher than 0.35
         * print the number of strings in sr whose C-G-ratio is higher than 0.35
         * print the length of the longest gene in sr
         */
        Integer longCount = 0;
        Integer CGRatioCount = 0;
        Integer longest = 0;
        Double CGR = 0.0;
        System.out.println("Number of gene strings: " + sr.size());
        
        for (String line : sr.data()){
            //if (line.length() > 9) {
            if (line.length() > 60) {
                System.out.println("More than 60 characters: " + line);
                longCount = longCount + 1;
            }
            if (cgRatio(line) > 0.35) {
                System.out.println("CG Ratio above 0.35: " + line);
                CGRatioCount = CGRatioCount + 1;
            }
            if (line.length() > longest) {
                longest = line.length();
            }
        }
        System.out.println("Number of strings in sr longer than 60 char: " + longCount);
        System.out.println("Number of strings in sr C-G-ratio above 0.35: " + CGRatioCount);
        System.out.println("Length of longest gene: " + longest);
    }
    
    public void testProcessGenes() {
    /**
     * This method will call your processGenes method on different test cases. Think of five DNA 
     * strings to use as test cases. These should include: one DNA string that has some genes longer 
     * than 9 characters, one DNA string that has no genes longer than 9 characters, one DNA string 
     * that has some genes whose C-G-ratio is higher than 0.35, and one DNA string that has some 
     * genes whose C-G-ratio is lower than 0.35. Write code in testProcessGenes to call processGenes 
     * five times with StorageResources made from each of your five DNA string test cases.
     */ 
    
    //               <-.35, ->     <-12ch,.35-><-15ch,.35   ->         <-18ch, .35     ->
    String dna = "GGGATGCCCTAACCCGTATGCCCGGGTAGATGCGTCGTCGTTGAGGGAAACCCATGCGTCGTCGTCGTTGATTT";
    StorageResource parsedGenes = new StorageResource();
    parsedGenes = getAllGenes(dna);
    processGenes(parsedGenes);
    
    //        <-.35, ->     <-12ch,.35->      <-18ch, .35     ->   <-15ch,.35   ->   
    dna = "GGGATGCCCTAACCCGTATGCCCGGGTAGAAACCCATGCGTCGTCGTCGTTGATTTATGCGTCGTCGTTGAGGG";
    parsedGenes = getAllGenes(dna);
    processGenes(parsedGenes);
    
    //        <-.35, ->     <-  .35->      <-     ->   <-  ->   
    dna = "GGGATGCCCTAACCCGTATGCGGTAGAAACCCATGTTTTGATTTATGTGAGGG";
    parsedGenes = getAllGenes(dna);
    processGenes(parsedGenes);

    //        <-     ->     <-12ch    ->      <-18ch          ->   <-15ch       ->   
    dna = "GGGATGTTTTAACCCGTATGCATATATAGAAACCCATGAATTATCGTCGTTGATTTATGAATCTTCTTTGAGGG";
    parsedGenes = getAllGenes(dna);
    processGenes(parsedGenes);

    //        <-  ->     <-  ->      <-     ->   <-  ->   
    dna = "GGGATGTAACCCGTATGTAGAAACCCATGTTTTGATTTATGTGAGGG";
    parsedGenes = getAllGenes(dna);
    processGenes(parsedGenes);

    }
    
    public void testFileProcessGenes() {
        //FileResource fr = new FileResource("brca1line.fa");
        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString().toUpperCase();
        StorageResource parsedGenes = new StorageResource();
        parsedGenes = getAllGenes(dna);
        processGenes(parsedGenes);
        
    }
}

