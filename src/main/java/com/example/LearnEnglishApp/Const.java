package com.example.LearnEnglishApp;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Const {

    ////Columns
    //words
    public static final String ID_WORD = "IdWord";
    public static final String WORD = "Word";
    public static final String TRANSLATE_RU = "TranslateRU";

    //motivationtext
    public static final String ID_TEXT = "IdText";
    public static final String MOTIVATION_TEXT = "MotivationText";

    ////Tables
    public static final String WORDS_TABLE = "words";
    public static final String MOTIVATION_TEXT_TABLE = "motivationtext";

    ////TextFilePatch
    //DataText
    public static final Path TEXT_FILES_PATH = Path.of(".\\DataText");

    //words
    public static final Path TEXT_FILE_WORDS_TABLE_PATH = Path.of(TEXT_FILES_PATH.toString() +"\\"+ WORDS_TABLE + ".xls");

    //motivationtext
    public static final Path TEXT_FILE_MOTIVATION_TEXT_PATH = Path.of(TEXT_FILES_PATH.toString() + "\\"+ MOTIVATION_TEXT_TABLE + ".xls");

    public static final String CONFIGS = ".\\Configs.txt";

    ////Methods
    public static HashMap<String, String> getAllColumnsAndTables() {
        HashMap<String, String> result = new HashMap<>();
        result.put(ID_WORD, WORDS_TABLE);
        result.put(WORD, WORDS_TABLE);
        result.put(TRANSLATE_RU, WORDS_TABLE);
        result.put(ID_TEXT, MOTIVATION_TEXT_TABLE);
        result.put(MOTIVATION_TEXT, MOTIVATION_TEXT_TABLE);
        return result;
    }

    public static ArrayList<String> getAllTables() {
        ArrayList<String> consts = new ArrayList<>();
        consts.add(WORDS_TABLE);
        consts.add(MOTIVATION_TEXT_TABLE);
        return consts;
    }

    public static Path getTextDataFilePatch(String table) {
        Path result = null;
        if (table.equals(WORDS_TABLE)) {
            result = TEXT_FILE_WORDS_TABLE_PATH;
        } else if (table.equals(MOTIVATION_TEXT_TABLE)) {
            result = TEXT_FILE_MOTIVATION_TEXT_PATH;
        }
        return result;
    }

    public static String getTableOfColumn(String column) {
        HashMap<String, String> allColumnsAndTables = getAllColumnsAndTables();
        StringBuilder table = new StringBuilder(allColumnsAndTables.get(column));
        return table.toString();
    }

    public static ArrayList<String> getColumnsOfTable(String table) {
        HashMap<String, String> allColumnsAndTables = getAllColumnsAndTables();
        ArrayList<String> list = new ArrayList<>();

        allColumnsAndTables.entrySet().stream().filter(entry -> table.equals(entry.getValue())).map(Map.Entry::getKey)
                .forEach(s -> list.add(s));
        return list;
    }
}
