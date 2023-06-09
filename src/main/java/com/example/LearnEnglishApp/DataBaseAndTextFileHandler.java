package com.example.LearnEnglishApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DataBaseAndTextFileHandler extends DatabaseHandler{
    @Override
    public String getWord(String column) {
        return super.getWord(column);
    }

    @Override
    public boolean hasNextWord(String column) {
        return super.hasNextWord(column);
    }

    @Override
    public ArrayList<String> getArrayListWords(String column) {
        return super.getArrayListWords(column);
    }
}
