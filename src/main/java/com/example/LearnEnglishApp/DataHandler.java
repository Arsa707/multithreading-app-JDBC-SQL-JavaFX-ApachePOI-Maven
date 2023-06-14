package com.example.LearnEnglishApp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public abstract class DataHandler extends Configs {
    abstract public ArrayList<String> getArrayListWords(String column) throws SQLException, IOException;
}
