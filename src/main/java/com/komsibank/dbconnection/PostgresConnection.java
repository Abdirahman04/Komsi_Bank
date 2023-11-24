package com.komsibank.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnection {
    public static void connect() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "abdirahman";
        String password = "Kiliman7504200235!";

        // JDBC variables for opening, closing, and managing connection
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }
}
