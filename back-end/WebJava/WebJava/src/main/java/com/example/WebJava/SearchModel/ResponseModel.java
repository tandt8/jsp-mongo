package com.example.WebJava.SearchModel;

import io.reactivex.rxjava3.core.Single;

import java.util.Set;

public class ResponseModel {
    public String filename;
    public Single<Set<byte[]>> byteFile;

    public ResponseModel(String filename,Single<Set<byte[]>> byteFile){
        this.filename = filename;
        this.byteFile = byteFile;
    }
}
