package com.komsibank.transactions;

import com.komsibank.BankApp;
import com.komsibank.Validations;
import com.komsibank.account.HomePage;
import com.komsibank.dbconnection.PostgresConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;

public class Deposit {
    public static void deposit(String accNumber) {
        BankApp.mainLogger.info("user: " + accNumber + " deposit page");
        System.out.println(">>>>>     DEPOSIT     <<<<<");
        System.out.println("How much do you want to deposit?");
        System.out.print(">>>   ");

        Scanner sc = new Scanner(System.in);
        String depositInput = sc.nextLine();
        double deposit = checkDeposit(depositInput,accNumber);

        PostgresConnection conn = new PostgresConnection();
        try (Connection connection = conn.connectToDatabase(conn.getUrl(), conn.getUser(), conn.getPassword())) {
            conn.changeBalance(connection,accNumber,true,deposit);
            conn.insertTransactionData(connection,accNumber,"deposit",deposit);
            BankApp.mainLogger.info("user: " + accNumber + " deposited " + deposit);

            System.out.println("Money deposited successfully");
            HomePage.home(accNumber);
        } catch (SQLException e) {
            System.out.println("Database connection failure.");
            BankApp.mainLogger.log(Level.SEVERE, "db connection error", e);
        }
    }
    public static double checkDeposit(String deposit,String accNumber) {
        if(!Validations.isNumber(deposit)) {
            System.out.println("Incorrect input!!");
            BankApp.mainLogger.info("user: " + accNumber + " checkDeposit: invalid input");
            deposit(accNumber);
        }

        double newDeposit = Double.parseDouble(deposit);

        if(newDeposit <= 0) {
            System.out.println("Deposit should be more than 0!!");
            BankApp.mainLogger.info("user: " + accNumber + " checkDeposit: invalid input");
            deposit(accNumber);
        }

        return newDeposit;
    }
}
