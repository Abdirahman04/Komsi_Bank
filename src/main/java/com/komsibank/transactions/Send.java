package com.komsibank.transactions;

import com.komsibank.BankApp;
import com.komsibank.account.HomePage;
import com.komsibank.dbconnection.PostgresConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;

public class Send {
    public static void send(String accNumber) {
        BankApp.mainLogger.info("user: " + accNumber + " send page");
        Scanner sc = new Scanner(System.in);

        System.out.println(">>>>>     SEND MONEY     <<<<<");

        System.out.println("Who do you want to send money to?");
        System.out.print(">>>>    ");
        String recipient = sc.nextLine();

        System.out.println("How much do you want to send?");
        System.out.print(">>>>    ");
        double amount = sc.nextDouble();

        PostgresConnection conn = new PostgresConnection();
        try (Connection connection = conn.connectToDatabase(conn.getUrl(), conn.getUser(), conn.getPassword())) {
            if(conn.isBalanceMore(connection,accNumber,amount)) {
                System.out.println("You do not have enough funds!!");
                send(accNumber);
            }

            conn.changeBalance(connection,accNumber,false,amount);
            conn.changeBalance(connection,recipient,true,amount);
            conn.insertTransferTransactionData(connection,accNumber,recipient,amount);
            BankApp.mainLogger.info("user: " + accNumber + " sent " + amount + " to user: " + recipient);

            System.out.println("Money sent successfully");
            HomePage.home(accNumber);
        } catch (SQLException e) {
            System.out.println("Database connection failure.");
            BankApp.mainLogger.log(Level.SEVERE,"db connection error", e);
        }
    }
}
