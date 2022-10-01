package com.example.WebJava.SearchModel;

import java.util.Set;

public class IndexObject {

        private String _file;
        private Set<String> _paragraph;
        public IndexObject(String file,Set<String> paragraph){
            _file =file;
            _paragraph = paragraph;
        }

        public String getFile() {
            return this._file;
        };
        public Set<String> getParagraph() {
            return this._paragraph;
        };
    }


