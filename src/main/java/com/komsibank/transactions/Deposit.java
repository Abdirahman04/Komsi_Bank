package com.komsibank.transactions;

import com.komsibank.account.HomePage;
import com.komsibank.dbconnection.PostgresConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Deposit {
    public static void deposit(String accNumber) {
        System.out.println(">>>>>     DEPOSIT     <<<<<");
        System.out.println("How much do you want to deposit?");
        System.out.print(">>>   ");

        Scanner sc = new Scanner(System.in);
        String deposit = sc.nextLine();
        double deposit2 = Double.parseDouble(deposit);

        boolean isDeposit = true;

        PostgresConnection conn = new PostgresConnection();
        try (Connection connection = conn.connectToDatabase(conn.getUrl(), conn.getUser(), conn.getPassword())) {
            conn.changeBalance(connection,accNumber,isDeposit,deposit2);
            conn.insertTransactionData(connection,accNumber,"deposit",deposit2);
            System.out.println("Money deposited successfully");
            HomePage.home(accNumber);
        } catch (SQLException e) {
            System.out.println("Database connection failure.");
            e.printStackTrace();
        }
    }
}
