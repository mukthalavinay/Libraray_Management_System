package com.developer.LibraryManagementSystem.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
        Connection connection = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            String url = "jdbc:mysql://localhost:3306/librarymanagementsystem";
            String username = "root";  
            String password = "17054-Cp-238";  
            connection = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found. Please add the driver library to your classpath.");
            throw new RuntimeException("Database driver not found!", e);
        }

        return connection;
    }
}
