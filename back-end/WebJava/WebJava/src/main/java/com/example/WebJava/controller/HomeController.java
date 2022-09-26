package com.example.WebJava.controller;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import vn.pipeline.*;
@RestController
public class HomeController {
    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello World";
    }

    @PostMapping("/InvertedIndex")
            public List<File> searchInverted(String searchWord) {
        List<File> files = new ArrayList<File>();
        return files;

    }
    @GetMapping("/invertedindex")
    public Set<String> setiingInvertedIndex() throws IOException {


        Observable<Path> stream = Observable.fromStream(Files.list(Paths.get("D:/jsp-mongo/back-end/WebJava/WebJava/src/main/resources/doccuments")));
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




       return newData;
    }
}
