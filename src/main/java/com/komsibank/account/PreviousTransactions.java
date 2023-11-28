package com.komsibank.account;

import com.komsibank.BankApp;
import com.komsibank.dbconnection.PostgresConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class PreviousTransactions {
    public static void transactions(String accNumber) {
        BankApp.mainLogger.info("user: " + accNumber + " previous transactions displayed");
        System.out.println(">>>>>     PREVIOUS TRANSACTIONS     <<<<<");

        PostgresConnection conn = new PostgresConnection();

        try (Connection connection = conn.connectToDatabase(conn.getUrl(), conn.getUser(), conn.getPassword())) {
            conn.getAllTransactions(connection,accNumber);
        } catch (SQLException e) {
            System.out.println("Database connection failure.");
            e.printStackTrace();
        }
    }
}
