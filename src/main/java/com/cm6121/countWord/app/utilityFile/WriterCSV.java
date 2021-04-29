package com.cm6121.countWord.app.utilityFile;



import java.io.File;

/**
 * Method that will create a directory in your root folder and return the directory as a String.
 * The CSV files that you are saving are expected to be in this path.
 */

public class WriterCSV {
    public String createDirectory(){
        String pathFile = System.getProperty("user.home")+"/StudentCSVSaved/";
        File dir = new File(pathFile);
        if(!dir.exists()){
            dir.mkdirs();
        }
        return pathFile;
    }


}