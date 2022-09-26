package com.example.WebJava.SearchModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IndexBuilder {
    public Set<String> dictionary;
    public Map<String, Map<Integer, Integer>> wordCount;
    public Map<String, List<Integer>> invertedIndex;
    private int documentCount;
    List<List<String>> documents;

    public ArrayList Index;

    public IndexBuilder(List<List<String>> documents, Set<String> dic){
        dictionary= new HashSet<String>();
        wordCount= new HashMap<String, Map<Integer, Integer>>();
        invertedIndex = new HashMap<String, List<Integer>>();
        this.documents=documents;
        Index= new ArrayList();
        dictionary=dic;
    }

    //Tao chi muc cho tai lieu co tu ghep

    public ArrayList build(){
        documentCount= documents.size();

        final Iterator<String> it= dictionary.iterator();
        while(it.hasNext()){
            String token= it.next();
            List<Integer> posting= new ArrayList<Integer>();
            Map<Integer, Integer> count= new HashMap<Integer, Integer>();

            for(int i=0;i<documents.size();i++){
                List<String> document=documents.get(i);
                if(document.contains(token)){
                    posting.add(i);
                }

                int occurence=Collections.frequency(document, token);
                count.put(i, occurence);
                wordCount.put(token, count);
            }
            invertedIndex.put(token, posting);
        }
        Index.add(invertedIndex);
        Index.add(wordCount);
        return Index;
    }

//    public ArrayList combine(ArrayList index1, ArrayList index2){
//        Map<String, Map<Integer, Integer>> wordCount1;
//        Map<String, List<Integer>> invertedIndex1;
//
//        Map<String, Map<Integer, Integer>> wordCount2;
//        Map<String, List<Integer>> invertedIndex2;
//
//        invertedIndex1= (Map<String, List<Integer>>)index1.get(0);
//        wordCount1= (Map<String, Map<Integer, Integer>>)index1.get(1);
//
//        invertedIndex2= (Map<String, List<Integer>>)index2.get(0);
//        wordCount2= (Map<String, Map<Integer, Integer>>)index2.get(1);
//
//        for(String token : invertedIndex2.keySet()){
//            if(!invertedIndex1.containsKey(token)){
//                //Them token va posting vao chi muc
//                List<Integer> posting= invertedIndex2.get(token);
//                invertedIndex1.put(token, posting);
//                //Them token va so luong xuat hien vao wordCount
//                Map<Integer, Integer> count= wordCount2.get(token);
//                wordCount1.put(token, count);
//
//            }
//            else{
//                Map<Integer, Integer> docFreq1= wordCount1.get(token);
//                Map<Integer, Integer> docFreq2= wordCount2.get(token);
//                Map<Integer, Integer> newDocFreq= new HashMap<Integer, Integer>();
//                for(Integer doc : docFreq2.keySet()){
//                    if(docFreq1.get(doc) > docFreq2.get(doc)){
//                        newDocFreq.put(doc, docFreq1.get(doc));
//                    }
//                    else{
//                        newDocFreq.put(doc, docFreq2.get(doc));
//                    }
//                }
//                wordCount1.put(token, newDocFreq);
//            }
//        }
//        ArrayList arr= new ArrayList();
//        arr.add(invertedIndex1);
//        arr.add(wordCount1);
//
//        return arr;
//    }

    public ArrayList combine(ArrayList index1, ArrayList index2) throws FileNotFoundException, UnsupportedEncodingException, IOException {

        String str= this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        int index= str.lastIndexOf("/web");
        str=str.substring(0, index);
        str=str.substring(1);
        str=str.replace("%20", " ");
        str+="/InvertedIndexFiles/";
        //Ghi du lieu ra file
        File file= new File(str+"InvertedIndex.txt");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"));

        Map<String, Map<Integer, Integer>> wordCount1;
        Map<String, List<Integer>> invertedIndex1;

        Map<String, Map<Integer, Integer>> wordCount2;
        Map<String, List<Integer>> invertedIndex2;

        invertedIndex1= (Map<String, List<Integer>>)index1.get(0);
        wordCount1= (Map<String, Map<Integer, Integer>>)index1.get(1);

        invertedIndex2= (Map<String, List<Integer>>)index2.get(0);
        wordCount2= (Map<String, Map<Integer, Integer>>)index2.get(1);

        for(String token : invertedIndex2.keySet()){
            if(!invertedIndex1.containsKey(token)){
                //Them token va posting vao chi muc
                List<Integer> posting= invertedIndex2.get(token);
                invertedIndex1.put(token, posting);
                //Them token va so luong xuat hien vao wordCount
                Map<Integer, Integer> count= wordCount2.get(token);
                wordCount1.put(token, count);

            }
            else{
                Map<Integer, Integer> docFreq1= wordCount1.get(token);
                Map<Integer, Integer> docFreq2= wordCount2.get(token);
                Map<Integer, Integer> newDocFreq= new HashMap<Integer, Integer>();
                for(Integer doc : docFreq2.keySet()){
                    if(docFreq1.get(doc) > docFreq2.get(doc)){
                        newDocFreq.put(doc, docFreq1.get(doc));
                    }
                    else{
                        newDocFreq.put(doc, docFreq2.get(doc));
                    }
                }
                wordCount1.put(token, newDocFreq);
                String line= new String();
                line =token+"\t";
                for(Integer doc : newDocFreq.keySet()){
                    Integer count=newDocFreq.get(doc);
                    line+=count+"\t"+doc+" ";
                }
                line = line.substring(0, line.length()-1);
                out.append(line).append("\r\n");
                out.flush();
            }
        }
        ArrayList arr= new ArrayList();
        arr.add(invertedIndex1);
        arr.add(wordCount1);

        return arr;
    }

    public void writeToFile(Map<String, List<Integer>> indexes,Map<String, Map<Integer, Integer>> counts ) throws FileNotFoundException, UnsupportedEncodingException, IOException{
        //Lay duong dan chua file chi muc nghich dao
        String str= this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        int index= str.lastIndexOf("/web");
        str=str.substring(0, index);
        str=str.substring(1);
        str=str.replace("%20", " ");
        str+="/InvertedIndexFiles/";
        //Ghi du lieu ra file
        File file= new File(str+"InvertedIndex.txt");
        final Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"));


        for(String token : counts.keySet()){
            String line= new String();
            line =token+"\t";
            Map<Integer, Integer> docFreq= counts.get(token);
            int tokenFreq= indexes.get(token).size();
            for(Integer doc : docFreq.keySet()){
                Integer count=docFreq.get(doc);
                line+=count+"\t"+doc+" ";
                //System.out.println("Token: "+token+" - Doc: "+doc+" - Count: "+count);

                /*Tinh tfidf
                double w=0.0;
                if(count>0){
                    w=1+Math.log(count);
                }
                Double value=w*Math.log(documentCount/tokenFreq);
                tfidf.add(new TfIdfBuilder(token, doc, value));*/
                //System.out.println("Token: "+token+" - Doc: "+doc+" - Value: "+value);
            }
            line = line.substring(0, line.length()-1);
            out.append(line).append("\r\n");
            out.flush();
        }
        out.close();
    }
}
