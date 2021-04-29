package com.cm6121.countWord.app;



public class Application {
    public static void main (String[] args) {
        String documentToRead = ClassLoader.getSystemClassLoader().getResource("FolderDocumentsToRead").getPath();
        System.out.println("The counting words application");
        System.out.println(documentToRead);

    }
}
