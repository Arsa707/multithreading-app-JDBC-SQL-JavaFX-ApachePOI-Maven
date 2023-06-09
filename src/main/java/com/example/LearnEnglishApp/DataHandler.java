package com.example.LearnEnglishApp;

import java.io.BufferedReader;
import java.util.ArrayList;

public abstract class DataHandler extends Configs {
    abstract public String getWord(String column);
    abstract public boolean hasNextWord(String column);
    abstract public ArrayList<String> getArrayListWords(String column);
}
