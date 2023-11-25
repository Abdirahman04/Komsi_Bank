package com.komsibank.transactions;

import com.komsibank.account.HomePage;
import com.komsibank.dbconnection.PostgresConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Withdraw {
    public static void withdraw(String accNumber) {
        System.out.println(">>>>>     WITHDRAW     <<<<<");
        System.out.println("How much do you want to withdraw?");
        System.out.print(">>>   ");

        Scanner sc = new Scanner(System.in);
        String withdraw = sc.nextLine();
        double withdraw2 = Double.parseDouble(withdraw);

        boolean isDeposit = false;

        PostgresConnection conn = new PostgresConnection();
        try (Connection connection = conn.connectToDatabase(conn.getUrl(), conn.getUser(), conn.getPassword())) {
            conn.changeBalance(connection,accNumber,isDeposit,withdraw2);
            conn.insertTransactionData(connection,accNumber,"withdrawal",withdraw2);
            System.out.println("Money withdrawn successfully");
            HomePage.home(accNumber);
        } catch (SQLException e) {
            System.out.println("Database connection failure.");
            e.printStackTrace();
        }
    }
}
