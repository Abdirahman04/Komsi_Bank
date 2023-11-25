package com.komsibank.dbconnection;

import com.komsibank.account.HomePage;
import com.komsibank.model.User;

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
    public User getUser(Connection connection, String accountNumber) throws SQLException {
        String sql = "SELECT first_name,last_name,email,account_number,balance FROM users WHERE account_number = ?";
        User user = new User();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, accountNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user.setFname(resultSet.getString("first_name"));
                    user.setLname(resultSet.getString("last_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setBalance(resultSet.getDouble("balance"));
                } else {
                    System.out.println("User not found");
                    HomePage.home(accountNumber);
                }
            }
        }

        return user;
    }

    public void changeBalance(Connection connection, String accountNumber, boolean isDeposit, double balance) throws SQLException {
        String updateSql = isDeposit
                ? "UPDATE users SET balance = balance + ? WHERE account_number = ?"
                : "UPDATE users SET balance = balance - ? WHERE account_number = ?";

        // Create a prepared statement
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            // Set parameters
            preparedStatement.setDouble(1, balance);
            preparedStatement.setString(2, accountNumber);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Amount updated successfully.");
            } else {
                System.out.println("User not found or no rows updated.");
            }
        }
    }
    public void insertTransactionData(Connection connection, String accNumber, String transType, double amount) throws SQLException {
        String sql = "INSERT INTO basictransactions(transaction_type,transaction_amount,account_number) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, transType);
            preparedStatement.setDouble(2, amount);
            preparedStatement.setString(3, accNumber);

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + affectedRows);
        }
    }
}
