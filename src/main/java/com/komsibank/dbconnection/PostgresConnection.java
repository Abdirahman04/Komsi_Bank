package com.komsibank.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostgresConnection {
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "abdirahman";
    private final String password = "Kiliman7504200235!";

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public static Connection connectToDatabase(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void insertUserData(Connection connection, String accountNumber, String firstName, String lastName, String email, String password) throws SQLException {
        String sql = "INSERT INTO users(account_number, first_name, last_name, email, password) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, accountNumber);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, password);

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + affectedRows);
        }
    }
}
