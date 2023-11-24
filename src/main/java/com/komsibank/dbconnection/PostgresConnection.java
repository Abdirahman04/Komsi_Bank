package com.komsibank.dbconnection;

import java.sql.*;

public class PostgresConnection {
    private final String url = "jdbc:postgresql://localhost:5432/komsi_bankdb";
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

    public Connection connectToDatabase(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void insertUserData(Connection connection, String accNumber, String firstName, String lastName, String email, double balance, String password) throws SQLException {
        String sql = "INSERT INTO users(account_number, first_name, last_name, email, balance, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, accNumber);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, email);
            preparedStatement.setDouble(5, balance);
            preparedStatement.setString(6, password);

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + affectedRows);
        }
    }

    public boolean doesUserExist(Connection connection, String accountNumber, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE account_number = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, accountNumber);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // true if a record is found, false otherwise
            }
        }
    }
}
