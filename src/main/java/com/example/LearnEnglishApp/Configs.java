package com.example.LearnEnglishApp;


public class Configs {
    //private static String dbHost = "127.0.0.1";
    private static String dbHost = "localhost";
    private static String dbPort = "3306";
    private static String dbUser = "root";
    private static String dbPass = "12345678";
    private static String dbName = "appdata";


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
