package com.cm6121.countWord.app.utilityFile;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    /**
     * Method that will return a List of Array of String that will contains the CSV content.
     * The method skip the first line of the CSV
     * You can implement another method, or use this one to read a CSV. This method is using OpenCSV
     * @param file, the CSV file
     * @return a List of String array
     */
    public List<String[]> readCSVMethod1(File file){
        List<String[]>strings = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new java.io.FileReader(file));
            reader.skip(1);
            strings = reader.readAll();
        }catch(IOException | CsvException e){
            System.out.println(e.getStackTrace());
        }
        return strings;
    }
}
