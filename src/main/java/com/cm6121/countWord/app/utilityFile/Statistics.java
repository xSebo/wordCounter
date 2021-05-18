package com.cm6121.countWord.app.utilityFile;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class Statistics {
    private Map<Integer, Map<String, Object>> statistics;

    public Map<String, Integer> getStatisticsTotal() {
        return statisticsTotal;
    }

    private Map<String, Integer> statisticsTotal;

    public Map<Integer, Map<String, Object>> getStatistics() {
        return statistics;
    }

    private String charsIn(List<String[]> readCSVMethod, int row){
        String chars = "";
        for(String[] s : readCSVMethod) {
            chars += s[row];
        }
        return chars;

    }

    public LinkedHashMap<String, Integer> sortMapDesc(Map<String, Integer> toSort){
        // SORT DESCENDING------------------------------------------------------
        Map<String, Integer> unSortedMap = toSort;

        //LinkedHashMap preserve the ordering of elements in which they are inserted
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();

        //Use Comparator.reverseOrder() for reverse ordering
        unSortedMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        // ----------------------------------------------------------------------------
        //Reference:[ACCESSED 17 MAY 2021] https://howtodoinjava.com/java/sort/java-sort-map-by-values/#:~:text=Sort%20Map%20by%20Values%20using%20Stream%20APIs&text=Entry%20class%20has%20static%20method,Comparator%20to%20use%20in%20sorting.
        return sortedMap;
    }

    private void totalWordCounts(){
        Map<String, Integer> finalTotal = new HashMap<>();
        for(int i=0; i<getStatistics().size(); i++){
            Map<String, Integer> tempMap = (Map<String, Integer>) getStatistics().get(i).get("wordCounts");
            for(Map.Entry<String, Integer> e : tempMap.entrySet()){
                if(!finalTotal.containsKey(e.getKey())){
                    finalTotal.put(e.getKey(),e.getValue());

                }else if(finalTotal.containsKey(e.getKey())){
                    int tempInt = e.getValue() + finalTotal.get(e.getKey());
                    finalTotal.remove(e.getKey());
                    finalTotal.put(e.getKey(),tempInt);
                }
            }
        }
        this.statisticsTotal = sortMapDesc(finalTotal);
    }

    private Map<String, Integer> countWords(List<String[]> stringList){
        Map<String, Integer> tempWords = new HashMap();

        for(String[] s : stringList){
            String tempFileString = s[1].replaceAll("[^a-zA-Z1-9 ]+"," ");
            String[] fullDoc = tempFileString.toLowerCase()
                    .split(" ");
            for(String temp : fullDoc){
                if (!tempWords.containsKey(temp) && temp.length() >=2) {
                    tempWords.put(temp, 1);
                } else if(tempWords.containsKey(temp) && temp.length() >=2){
                    int tempInt = tempWords.get(temp) + 1;
                    tempWords.remove(temp);
                    tempWords.put(temp, tempInt);
                }
            }
        }

        return sortMapDesc(tempWords);
    }

    public int totalTimes(String word){
        word = word.toLowerCase();
        int counter = 0;
        for(int i=0;i < getStatistics().size();i++){
            Map<String, Integer> wordCounts = (Map<String, Integer>) getStatistics().get(i).get("wordCounts");
            try{
                counter += wordCounts.get(word);
            }catch(Exception e){
                //e.printStackTrace();
            }
        }
        return counter;
    }

    private void genStats() throws UnsupportedEncodingException {
        Map<Integer, Map<String, Object>> allData = new HashMap<>();
        FileReader fr = new FileReader();
        for(File f:fr.getDirFiles()) {
            String finalTempInt = String.valueOf(this.charsIn(fr.readCSVMethod1(f), 1).length());
            String finalTitle = this.charsIn(fr.readCSVMethod1(f), 0);
            String finalDate = this.charsIn(fr.readCSVMethod1(f), 2);
            allData.put(ArrayUtils.indexOf(fr.getDirFiles(),f),new HashMap(){{
                put("fileName", f.getName());
                put("totalCharacters",finalTempInt);
                put("title", finalTitle);
                put("publishingDate", finalDate);
                put("wordCounts", countWords(fr.readCSVMethod1(f)));
                    }});
        }
        this.statistics = allData;
    }

    public String totalWordString(){
        String finalString = "Most popular words:\n";
        int counter = 0;
        for(Map.Entry<String, Integer> entry : getStatisticsTotal().entrySet()){
            if(counter>19){
                break;
            }
            finalString += "Word: "
                    + entry.getKey()
                    + ", Occurrences: "
                    + entry.getValue()
                    + "\n";
            counter++;
        }
        return finalString;
    }
    public String totalIndividualWordString(){
        String finalString = "Most popular words:\n";
        for(int i = 0; i<getStatistics().size(); i++){
            Map<String, Integer> tempWords = (Map<String, Integer>) getStatistics().get(i).get("wordCounts");
            int counter=0;
            finalString += "\n" + getStatistics().get(i).get("fileName") + "\n";
            for(Map.Entry<String, Integer> entry : tempWords.entrySet()){
                if(counter>19){
                    break;
                }
                finalString += "Word: "
                        + entry.getKey()
                        + ", Occurrences: "
                        + entry.getValue()
                        + "\n";
                counter++;
            }
        }
        return finalString;
    }

    @Override
    public String toString(){
        String finalString = "";
        try {
            for(int i = 0; i < getStatistics().size(); i++){
                finalString +=  "File Name: "
                        + getStatistics().get(i).get("fileName");
                finalString += ", Title: "
                        + getStatistics().get(i).get("title");
                finalString += ", Creation Date: "
                        + getStatistics().get(i).get("publishingDate");
                finalString += ", Total Characters: "
                        + getStatistics().get(i).get("totalCharacters");
                finalString += "\n";
            }
            finalString += "Number of files: " + getStatistics().size();
        }catch(Exception e){
            e.printStackTrace();
        }
        return finalString;
    }

    public Statistics(){
        try {
            this.genStats();
            this.totalWordCounts();
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }
}
