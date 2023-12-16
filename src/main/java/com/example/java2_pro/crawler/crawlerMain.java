package com.example.java2_pro.crawler;

import java.io.IOException;
import java.sql.SQLException;

public class crawlerMain {
    private static final String SQL_HOST = "localhost";
    private static final String SQL_USER = "checker";
    private static final String SQL_PASSWORD = "123456";
    private static final String SQL_DATABASE = "stackoverflow_db";
    private static final int SQL_PORT = 5432;

    public static void main(String[] args) throws SQLException, IOException {
        databaseService databaseService =
            new databaseService(SQL_HOST,SQL_PORT,SQL_USER,SQL_PASSWORD,SQL_DATABASE);
        dataCollector dataCollector = new dataCollector(databaseService, 100, 100, 1000);
        databaseService.connect();
        databaseService.createTable();
        databaseService.disableForeignKeyCheck();
        dataCollector.collectData();
        databaseService.enableForeignKeyCheck();
        databaseService.close();
    }
}
