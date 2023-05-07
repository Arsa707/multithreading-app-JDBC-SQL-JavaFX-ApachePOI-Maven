package com.example.LearnEnglishApp;

import java.sql.*;


public class  DatabaseHandler extends Configs{
    Connection dbConnection;
    public Connection getDbConnection() throws  ClassNotFoundException, SQLException{

          String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
          Class.forName("com.mysql.cj.jdbc.Driver");
          dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

      return  dbConnection;
    }

    public ResultSet getWord(){
        ResultSet resultSet = null;
        String stringSQL =  "SELECT * FROM "+Const.WORDS_TABLE;

        try {
            Statement statement = getDbConnection().createStatement();
            resultSet = statement.executeQuery(stringSQL);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        };
        return resultSet;
    }

    public boolean СheckSelection(String selectTextOfButton, String actualTextOfQuestion){
        boolean result = false;

        ResultSet resultSet = null;
        String stringSQL =  "SELECT * FROM "+Const.WORDS_TABLE+" WHERE TranslateRU = +\""+selectTextOfButton+"\""+" AND "+Const.WORD+" = \""+actualTextOfQuestion+"\"";

        try {
            Statement statement = getDbConnection().createStatement();
            resultSet = statement.executeQuery(stringSQL);
            result = resultSet.next();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        };

        return result;
    }
}
