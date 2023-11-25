package com.komsibank.dbconnection;

import com.komsibank.account.HomePage;
import com.komsibank.model.User;

import java.sql.*;

public class PostgresConnection {

    public String getUrl() {
        return "jdbc:postgresql://localhost:5432/komsi_bankdb";
    }

    public String getUser() {
        return "abdirahman";
    }

    public String getPassword() {
        return "Kiliman7504200235!";
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

    public void insertTransferTransactionData(Connection connection, String sender, String recipient, double amount) throws SQLException {
        String sql = "INSERT INTO transfertransactions(transfer_transaction_amount,sender_account_number,recipient_account_number) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, sender);
            preparedStatement.setString(3, recipient);

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + affectedRows);
        }
    }

    public void getAllTransactions(Connection connection, String accNumber) throws SQLException {
        String basicTransactionSql = "SELECT * FROM basictransactions where account_number = ?";
        try (PreparedStatement preparedStatement1 = connection.prepareStatement(basicTransactionSql)) {
            preparedStatement1.setString(1, accNumber);
            try (ResultSet resultSet1 = preparedStatement1.executeQuery()) {
                while (resultSet1.next()) {
                    String transactionType = resultSet1.getString("transaction_type");
                    double transactionAmount = resultSet1.getDouble("transaction_amount");
                    System.out.println("   +------------------------------------------");
                    System.out.println("   |A " + transactionType+" of "+transactionAmount+" was made.");
                    System.out.println("   +------------------------------------------");
                }
            }
        }
        String senderTransactionSql = "SELECT * FROM transfertransactions where sender_account_number = ?";
        try (PreparedStatement preparedStatement2 = connection.prepareStatement(senderTransactionSql)) {
            preparedStatement2.setString(1, accNumber);
            try (ResultSet resultSet2 = preparedStatement2.executeQuery()) {
                while (resultSet2.next()) {
                    String recipient = resultSet2.getString("recipient_account_number");
                    double transactionAmount = resultSet2.getDouble("transfer_transaction_amount");
                    System.out.println("   +------------------------------------------");
                    System.out.println("   |"+transactionAmount+" was sent to "+recipient);
                    System.out.println("   +------------------------------------------");
                }
            }
        }
        String recipientTransactionSql = "SELECT * FROM transfertransactions where recipient_account_number = ?";
        try (PreparedStatement preparedStatement3 = connection.prepareStatement(recipientTransactionSql)) {
            preparedStatement3.setString(1, accNumber);
            try (ResultSet resultSet3 = preparedStatement3.executeQuery()) {
                while (resultSet3.next()) {
                    String sender = resultSet3.getString("sender_account_number");
                    double transactionAmount = resultSet3.getDouble("transfer_transaction_amount");
                    System.out.println("   +------------------------------------------");
                    System.out.println("   |Received "+transactionAmount+" from "+sender);
                    System.out.println("   +------------------------------------------");
                }
            }
        }
    }
    public void deleteAccount(Connection connection, String accNumber) throws SQLException {
        String deleteSql = "DELETE FROM users WHERE account_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setString(1, accNumber);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("User not found or no rows deleted.");
            }
        }
        String deleteSql2 = "DELETE FROM basictransactions WHERE account_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql2)) {
            preparedStatement.setString(1, accNumber);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User transactions information deleted successfully.");
            } else {
                System.out.println("User not found or no rows deleted.");
            }
        }
    }
    public boolean isBalanceMore(Connection connection, String accountNumber, double balance) throws SQLException {
        String selectSql = "SELECT balance FROM users WHERE account_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, accountNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double currentNumber = resultSet.getDouble("balance");
                    return balance > currentNumber;
                } else {
                    System.out.println("User not found in the database.");
                    return false;
                }
            }
        }
    }
}
