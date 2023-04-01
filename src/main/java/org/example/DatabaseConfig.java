package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String DB_URL = "jdbc:h2:file:./myapp-db;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE";
    private static final String DB_USERNAME = "myuser";
    private static final String DB_PASSWORD = "mypassword";


    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("H2 Driver not found", e);
        }
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}
