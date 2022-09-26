package com.example.WebJava.SearchModel;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
public class Document {
    List<String> document;
    List<List<String>> documents;
    public Document(){
        documents= new ArrayList<List<String>>();
    }

    public List<List<String>> build(String path, boolean single) throws IOException{

        //Lay ten file tu thu muc luu tru
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println(file.getAbsolutePath());
                document= new ArrayList<String>();
                byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath().toString()));
                String str= new String(encoded, "UTF-8");

                String[] words = str.split("\\s+");
                for (int i = 0; i < words.length; i++) {
                    Charset.forName("UTF-8").encode(words[i]);
                    String newString= new String(words[i].getBytes("UTF-8"), "UTF-8");
                    if(single)
                        newString= newString.replaceAll("[^\\p{L}\\p{Nd}]", "");
                    document.add(newString.toLowerCase());
                    //System.out.println(document.get(i)+" ");
                }
                documents.add(document);
            }
        }
        return documents;
    }

    public List<List<String>> build1(String query, boolean single) throws IOException{
        document= new ArrayList<String>();

        String[] words = query.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            Charset.forName("UTF-8").encode(words[i]);
            String newString= new String(words[i].getBytes("UTF-8"), "UTF-8");
            if(single)
                newString= newString.replaceAll("[^\\p{L}\\p{Nd}]", "");
            document.add(newString.toLowerCase());
            //System.out.println(document.get(i)+" ");
        }
        documents.add(document);
        return documents;
    }
}
