package com.cm6121.countWord.app.utilityFile;



import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Method that will create a directory in your root folder and return the directory as a String.
 * The CSV files that you are saving are expected to be in this path.
 */

public class WriterCSV {
    private final String saveDir = System.getProperty("user.home")+"\\StudentCSVSaved\\";
    // Test comment 9
    private void createDirectory(){
        File dir = new File(saveDir);
        if(!dir.exists()){
            dir.mkdirs();
        }else{
            try {
                FileUtils.cleanDirectory(dir);
            }catch(IOException e){
                System.out.println("File already open in another program!");
            }
        }
    }

    public void writeCorpusMaps(Statistics stats){
        String fileName = "CSVAllDocuments_allWords.csv";
        File fileToWrite = new File(this.saveDir
                + "/" + fileName);
        BufferedWriter writer = null;
        boolean titleWritten = false;
        try {
            writer = new BufferedWriter(new FileWriter(fileToWrite));
            for (Map.Entry<String, Integer> e : stats.getStatisticsTotal().entrySet()) {
                if(!titleWritten){
                    titleWritten = true;
                    writer.write("Word" + "," + "Number of occurrences");
                    writer.newLine();
                }
                writer.write(e.getKey() + "," + e.getValue());
                writer.newLine();
                writer.flush();
            }
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void writeIndividualMaps(Statistics stats){
        for(int i = 0; i < stats.getStatistics().size(); i++){
            boolean titleWritten = false;


            // SORT ASCENDING------------------------------------------------------------
            Map<String, Integer> unSortedMap = (Map <String, Integer>)stats.getStatistics().get(i).get("wordCounts");

            //LinkedHashMap preserve the ordering of elements in which they are inserted
            LinkedHashMap<String, Integer> wordCounts = new LinkedHashMap<>();

            unSortedMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEachOrdered(x -> wordCounts.put(x.getKey(), x.getValue()));
            // ----------------------------------------------------------------------------
            //Reference:[ACCESSED 17 MAY 2021] https://howtodoinjava.com/java/sort/java-sort-map-by-values/#:~:text=Sort%20Map%20by%20Values%20using%20Stream%20APIs&text=Entry%20class%20has%20static%20method,Comparator%20to%20use%20in%20sorting.

            String fileName = (String) stats.getStatistics().get(i).get("title");
            fileName = fileName.replaceAll(" ", "_") + "_allWords.csv";

            File fileToWrite = new File(this.saveDir
                    + "/" + fileName);
            BufferedWriter writer = null;
            try{
                try {
                    writer = new BufferedWriter(new FileWriter(fileToWrite));
                }catch(IOException e){
                    System.out.println("Document is open in another program!");
                }
                for(Map.Entry<String, Integer> e : wordCounts.entrySet()){
                    if (!titleWritten){
                        String year = (String) stats.getStatistics()
                                .get(i)
                                .get("publishingDate");
                        if(i==2){
                            year = year.substring(1);
                        }
                        writer.write(stats.getStatistics()
                                .get(i)
                                .get("title") +
                                "," +
                                year);

                        writer.newLine();
                        titleWritten = true;
                    }
                    writer.write(e.getKey() + "," + e.getValue());
                    writer.newLine();
                    writer.flush();
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public WriterCSV(){
        this.createDirectory();
    }

}
