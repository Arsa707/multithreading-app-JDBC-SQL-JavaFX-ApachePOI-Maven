package com.example.LearnEnglishApp;


import java.util.HashMap;
import java.util.HashSet;

public class Configs {
    private static String dbHost = "localhost";
    private static String dbPort = "3306";
    private static String dbUser = "root";
    private static String dbPass = "12345678";
    private static String dbName = "appdata";

    private static int textDisplayTimeout = 10000;

    private static boolean fileMode = true;

    public static void setFileMode(boolean fileMode){
        Configs.fileMode = fileMode;
    }

    public static void setFileMode(String fileMode){
        Configs.fileMode = Boolean.valueOf(fileMode);
    }

    public static HashMap<String,String> getConfigs(){
        HashMap<String, String> result = new HashMap<>();
        result.put("dbName", dbName);
        result.put("dbHost", dbHost);
        result.put("dbPass", dbPass);
        result.put("dbUser", dbUser);
        result.put("dbPort", dbPort);
        result.put("fileMode", String.valueOf(fileMode));
        result.put("textDisplayTimeout", String.valueOf(textDisplayTimeout));
        return result;
    }

    public static int getTextDisplayTimeout() {
        return textDisplayTimeout;
    }

    public static boolean isFileMode() {
        return fileMode;
    }

    public static void setConfig(String key, String val ){
        String config = key.replaceAll(" ","");
        String value = val.replaceAll(" ", "");
        if(config.equals("dbName")) setDbName(value);
        if(config.equals("dbHost")) setDbHost(value);
        if(config.equals("dbPass")) setDbPass(value);
        if(config.equals("dbPort")) setDbPort(value);
        if(config.equals("dbUser")) setDbUser(value);
        if(config.equals("fileMode")) setFileMode(value);
        if(config.equals("textDisplayTimeout")) setTextDisplayTimeout(Integer.parseInt(value));
        }

    public static void setTextDisplayTimeout(int textDisplayTimeout) {
        Configs.textDisplayTimeout = textDisplayTimeout;
    }

    public static String getDbHost() {
        return dbHost;
    }

    public static void setDbHost(String dbHost) {
        Configs.dbHost = dbHost;
    }

    public static String getDbPort() {
        return dbPort;
    }

    public static void setDbPort(String dbPort) {
        Configs.dbPort = dbPort;
    }

    public static String getDbUser() {
        return dbUser;
    }

    public static void setDbUser(String dbUser) {
        Configs.dbUser = dbUser;
    }

    public static String getDbPass() {
        return dbPass;
    }

    public static void setDbPass(String dbPass) {
        Configs.dbPass = dbPass;
    }

    public static String getDbName() {
        return dbName;
    }

    public static void setDbName(String dbName) {
        Configs.dbName = dbName;
    }
}
