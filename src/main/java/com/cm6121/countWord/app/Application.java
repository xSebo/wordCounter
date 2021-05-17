package com.cm6121.countWord.app;


import com.cm6121.countWord.app.utilityFile.FileReader;
import com.cm6121.countWord.app.utilityFile.Statistics;
import com.cm6121.countWord.app.utilityFile.WriterCSV;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Scanner;

public class Application {
    public static void main (String[] args) {
        Statistics stats = new Statistics();
        WriterCSV wc = new WriterCSV();
        try {
            wc.writeIndividualMaps(stats);
            wc.writeCorpusMaps(stats);
            boolean continueLoop = true;
            while(continueLoop) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("----------------------------------");
                System.out.println("1. Display the names and the number of documents");
                System.out.println("2. Display the number of occurrences of the words for each document");
                System.out.println("3. Enter a word, and display the number of occurrences of it in each document");
                System.out.println("4. Display the number of occurrences for each word in the whole corpus");
                System.out.println("5. Exit");
                System.out.println("----------------------------------");
                String userResponse = scanner.nextLine();
                switch (userResponse) {
                    case "1":
                        System.out.println("Document names: ");
                        System.out.println();
                        System.out.println("Total number of documents: "
                                + stats.getStatistics().size());
                        for (int i = 0; i < stats.getStatistics().size(); i++) {
                            System.out.println("File name: "
                                    + stats.getStatistics().get(i).get("fileName")
                                    + ", Title: "
                                    + stats.getStatistics().get(i).get("title"));
                        }
                        break;
                    case "2":
                        System.out.println("Number of occurrences");
                        System.out.println();
                        System.out.println(stats.totalIndividualWordString());
                        break;

                    case "3":
                        System.out.println("Enter the word you'd like to lookup: ");
                        String wordToSearch = scanner.nextLine();
                        System.out.println("The word \""
                                + wordToSearch
                                + "\" appears "
                                + stats.totalTimes(wordToSearch.toLowerCase())
                                + " time(s).");
                        break;

                    case "4":
                        System.out.println(stats.totalWordString());
                        break;

                    case "5":
                        continueLoop = false;
                        break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
