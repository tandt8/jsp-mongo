package com.example.WebJava.SearchModel;

public class IndexModel {
    private String _file;
    private String _paragraph;
    public IndexModel(String file, String paragraph){
        _file =file;
        _paragraph = paragraph;
    }

   public String getFile() {
        return this._file;
   };
    public String getParagraph() {
        return this._paragraph;
    };
}
