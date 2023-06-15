package com.example.LearnEnglishApp;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class DatabaseHandler extends DataHandler {

    private static Connection dbConnection;
    private static boolean reconnectionQuestionAsked = false;

    public DatabaseHandler(){
        if (getDbConnection()==null) setDbConnection();
    }

    public boolean isReconnectionQuestionAsked() {
        return reconnectionQuestionAsked;
    }

    public void setDbConnection() {
        try {
            String connectionString = "jdbc:mysql://" + getDbHost() + ":" + getDbPort() + "/" + getDbName();
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConnection = DriverManager.getConnection(connectionString, getDbUser(), getDbPass());
        } catch (SQLException e) {
            e.printStackTrace();
            dbConnection = null;
            reconnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            dbConnection = null;
            reconnection();
        }
    }

    public void setReconnectionQuestionAsked(boolean reconnectionQuestionAsked) {
        DatabaseHandler.reconnectionQuestionAsked = reconnectionQuestionAsked;
    }

    public boolean reconnection() {
        boolean result = false;
        boolean needReconnection = false;
        if (dbConnection == null) needReconnection = true;
        else {
            try {
                needReconnection = !dbConnection.isValid(100);
            } catch (SQLException e) {
                e.printStackTrace();
                needReconnection = true;
            }
        }
        try {
            if (needReconnection) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("");
                alert.setHeaderText("");
                alert.setContentText("Соединение с сервером потеряно. \n Попробовать переподключиться?");
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

                while (!isReconnectionQuestionAsked()) {
                    Optional<ButtonType> answer = alert.showAndWait();
                    if (answer.get() == ButtonType.YES) {
                        setDbConnection();
                        if ((dbConnection != null)) {
                            result = true;
                            break;
                        }
                    }
                    setReconnectionQuestionAsked(true);
                }
            } else {
                setDbConnection();
                if (dbConnection.isValid(100)) result = true;
                else reconnection();
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Connection getDbConnection() {
        return dbConnection;
    }

    public ResultSet getResulSet(String stringSQL) throws SQLException {
        ResultSet resultSet = null;
        Connection connection = getDbConnection();
        if ((connection != null)) {
            if (connection.isValid(100)) {
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(stringSQL);
            }
        }
        return resultSet;
    }

    public boolean CheckSelection(String selectTextOfButton, String actualTextOfQuestion) throws SQLException, IOException {
        boolean result = false;
        ResultSet resultSet;
        String stringSQL = "SELECT * FROM " + Const.WORDS_TABLE + " WHERE "+Const.TRANSLATE_RU+" = +\"" + selectTextOfButton + "\"" + " AND " + Const.WORD + " = \"" + actualTextOfQuestion + "\"";
        resultSet = getResulSet(stringSQL);
        result = resultSet.next();

        return result;
    }

    @Override
    public ArrayList<String> getArrayListWords(String column) throws SQLException, IOException {
        ArrayList<String> words = new ArrayList<>();
        ResultSet resultSet;
        String stringSQL = "SELECT * FROM " + Const.WORDS_TABLE;
        resultSet = getResulSet(stringSQL);
        while (resultSet.next()) {
            words.add(resultSet.getString(column));
        }
        return words;
    }
}
