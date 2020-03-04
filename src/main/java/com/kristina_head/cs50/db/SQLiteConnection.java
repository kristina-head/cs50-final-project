package com.kristina_head.cs50.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:food.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(SQLITE_CONNECTION_STRING);
    }
}
