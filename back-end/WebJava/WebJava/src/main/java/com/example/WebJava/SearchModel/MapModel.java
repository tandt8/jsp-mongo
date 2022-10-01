package com.example.WebJava.SearchModel;

import io.reactivex.rxjava3.core.Single;

import java.util.List;

public class MapModel {
    private String _word;
    private String _docs;

    public MapModel(String word,String docs){
        _word =word;
        _docs = docs;
    }

    public String getWord(){
        return _word;
    }
    public String getDocs(){
        return _docs;
    }

}
