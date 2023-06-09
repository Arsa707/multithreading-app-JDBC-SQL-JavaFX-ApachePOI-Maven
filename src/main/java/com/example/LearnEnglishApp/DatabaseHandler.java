package com.example.LearnEnglishApp;

import java.sql.*;
import java.util.ArrayList;


public class DatabaseHandler extends DataHandler {
    private static TypeHandler typeHandler;
    private static Connection dbConnection;

    public synchronized Connection getDbConnection() {
        if (dbConnection == null) {
            try {
                String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
                Class.forName("com.mysql.cj.jdbc.Driver");
                dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return dbConnection;
        }
        return dbConnection;
    }

    @Override
    public String getWord(String column) {
        ResultSet resultSet = null;
        String result = "";
        String stringSQL = "SELECT * FROM " + Const.WORDS_TABLE;
        resultSet = getResulSet(stringSQL);
        try {
            result = resultSet.getString(column);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean hasNextWord(String column) {
        ResultSet resultSet = null;
        boolean result = false;
        String stringSQL = "SELECT * FROM " + Const.WORDS_TABLE;
        resultSet = getResulSet(stringSQL);
        try {
            result = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet getResulSet(String stringSQL) {
        ResultSet resultSet = null;
        try (Statement statement = getDbConnection().createStatement()) {
            resultSet = statement.executeQuery(stringSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public boolean CheckSelection(String selectTextOfButton, String actualTextOfQuestion) {
        boolean result = false;
        ResultSet resultSet = null;
        String stringSQL = "SELECT * FROM " + Const.WORDS_TABLE + " WHERE TranslateRU = +\"" + selectTextOfButton + "\"" + " AND " + Const.WORD + " = \"" + actualTextOfQuestion + "\"";
        resultSet = getResulSet(stringSQL);
        try {
            result = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<String> getArrayListWords(String column) {
        ArrayList<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();
        while (hasNextWord(column)) {
            word.append(getWord(column));
        }
        return words;
    }
}
