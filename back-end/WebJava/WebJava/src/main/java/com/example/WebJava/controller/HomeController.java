package com.example.WebJava.controller;

import com.example.WebJava.SearchModel.IndexModel;
import com.example.WebJava.SearchModel.InputModel;
import com.example.WebJava.SearchModel.MapModel;
import com.example.WebJava.SearchModel.ResponseModel;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import vn.pipeline.*;
@RestController
public class HomeController {
    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello World";
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @PostMapping(path = "/InvertedIndex")
            public  Single<Set<IndexModel>> searchInverted(@RequestBody InputModel searchWord) throws IOException {
       Map<String,String> mapIndex = new HashMap<String,String>();
      try {
          BufferedReader reader = new BufferedReader(new FileReader("D:/jsp-mongo/back-end/WebJava/WebJava/src/main/resources/mapIndex.txt"));
          mapIndex =reader.lines().map(e -> e.split(":")).collect(Collectors.toMap(x-> x[0].substring(1),x->x[1]));


      } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
      }
      var newdata = searchWord.word;
        String[] annotators = {"wseg", "pos", "ner", "parse"};
        VnCoreNLP pipeline = new VnCoreNLP(annotators);
        Annotation annotation = new Annotation(newdata.replace("."," ").replace(","," ").replaceAll("\\W", " "));
        pipeline.annotate(annotation);

        var result = annotation.getWords().stream().map(e-> e.getForm().replace("_"," ")).collect(Collectors.toSet());
        Map<String, String> finalMapIndex = mapIndex;
        var files = result.stream().map(e-> {
             var listfile = finalMapIndex.get(e);

              return listfile == null ? new String[0]: listfile.replaceAll(" ","").split(",") ;
        }).flatMap(Stream::of).collect(Collectors.toSet());

       var listFiles =Observable.fromIterable(files).map(e -> {

           BufferedReader reader = new BufferedReader(new FileReader("D:/jsp-mongo/back-end/WebJava/WebJava/src/main/resources/doccuments/" + e +".txt"));
           var para = reader.lines().collect(Collectors.joining(""));
           return new IndexModel(e,para);
       }).collect(Collectors.toSet());


        return listFiles;



    }


    @GetMapping("/invertedindex")
    public String setiingInvertedIndex() throws IOException {


        Observable<Path> stream = Observable.fromStream(Files.list(Paths.get("D:/jsp-mongo/back-end/WebJava/WebJava/src/main/resources/doccuments"))).cache();
      var words = new ArrayList<Set<String>>();

      var strings = stream.map(e -> e.getFileName().toString()).map(e -> {
            BufferedReader reader = new BufferedReader(new FileReader("D:/jsp-mongo/back-end/WebJava/WebJava/src/main/resources/doccuments/" + e));
            return reader.lines().collect(Collectors.joining(" "));

        }).map(e->{
          String[] annotators = {"wseg", "pos", "ner", "parse"};
          VnCoreNLP pipeline = new VnCoreNLP(annotators);
          Annotation annotation = new Annotation(e.replace("."," ").replace(","," ").replaceAll("\\W", " "));
          pipeline.annotate(annotation);
          Set<String> set = new HashSet<String>();
           Observable.fromIterable(annotation.getWords()).map(event ->event.getForm()).subscribe(event ->{
               set.add(event);
           });
          return set;
      }).subscribe(e->{
          words.add(e);
              }
      );
      var newData = words.stream().flatMap(e->e.stream()).map(e ->e.replace("_"," ")).collect(Collectors.toSet());
       List<IndexModel> indexs = new ArrayList<IndexModel>();
        var models = stream.map(e -> e.getFileName().toString()).map(e -> {
            BufferedReader reader = new BufferedReader(new FileReader("D:/jsp-mongo/back-end/WebJava/WebJava/src/main/resources/doccuments/" + e));
            var paragraph =reader.lines().collect(Collectors.joining(" "));
            return new IndexModel(e,paragraph);}).toList().subscribe(e ->{
                indexs.addAll(e);
        });
     var newMap =  newData.stream().map(e -> {
          List<String> docIndexs = new ArrayList<String>();
            Observable.fromIterable(indexs).filter(event -> event.getParagraph().contains(e)).map(index -> index.getFile()).map(event ->event.toString()).toList().subscribe(event ->{
               docIndexs.addAll(event);
           });
           return new MapModel(e, docIndexs.stream().collect(Collectors.joining(",")).replaceAll(".txt",""));
       }).collect(Collectors.toList());
     var result =  newMap.stream().map(e -> e.getWord() + ": " + e.getDocs()).collect(Collectors.joining("\n "));
        try {
            File myObj = new File("D:/jsp-mongo/back-end/WebJava/WebJava/src/main/resources/mapIndex.txt");
            FileWriter myWriter = new FileWriter("D:/jsp-mongo/back-end/WebJava/WebJava/src/main/resources/mapIndex.txt");
            myWriter.write(result);
            myWriter.close();
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
       return result;
    }
}
