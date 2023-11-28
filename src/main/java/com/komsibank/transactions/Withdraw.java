package com.komsibank.transactions;

import com.komsibank.BankApp;
import com.komsibank.Validations;
import com.komsibank.account.HomePage;
import com.komsibank.dbconnection.PostgresConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;

public class Withdraw {
    public static void withdraw(String accNumber) {
        BankApp.mainLogger.info("user: " + accNumber + " withdraw page");
        PostgresConnection conn = new PostgresConnection();

        System.out.println(">>>>>     WITHDRAW     <<<<<");
        System.out.println("How much do you want to withdraw?");

        System.out.print(">>>   ");
        Scanner sc = new Scanner(System.in);
        String withdrawInput = sc.nextLine();
        double withdraw = checkWithdrawal(withdrawInput,accNumber,conn);

        try (Connection connection = conn.connectToDatabase(conn.getUrl(), conn.getUser(), conn.getPassword())) {
            conn.changeBalance(connection,accNumber,false,withdraw);
            conn.insertTransactionData(connection,accNumber,"withdrawal",withdraw);
            BankApp.mainLogger.info("user: " + accNumber + " withdrew " + withdraw);
            BankApp.transactionsLogger.info("user: " + accNumber + " withdrew " + withdraw);

            System.out.println("Money withdrawn successfully");

            HomePage.home(accNumber);
        } catch (SQLException e) {
            System.out.println("Database connection failure.");
            BankApp.mainLogger.log(Level.SEVERE, "db connection error", e);
        }
    }
    public static double checkWithdrawal(String withdrawal,String accNumber,PostgresConnection conn) {
        if(!Validations.isNumber(withdrawal)) {
            System.out.println("Invalid input!!");
            BankApp.mainLogger.info("user: " + accNumber + " checkWithdrawal: invalid input");
            withdraw(accNumber);
        }

        double newWithdrawal = Double.parseDouble(withdrawal);

        if(newWithdrawal <= 0) {
            System.out.println("Withdrawal should be more than 0!!");
            BankApp.mainLogger.info("user: " + accNumber + " checkWithdrawal: invalid input");
            withdraw(accNumber);
        }

        try (Connection connection = conn.connectToDatabase(conn.getUrl(), conn.getUser(), conn.getPassword())) {
            if(conn.isBalanceMore(connection,accNumber,newWithdrawal)) {
                System.out.println("You don't have enough funds!!");
                BankApp.mainLogger.info("user: " + accNumber + " checkWithdrawal: invalid input");
                withdraw(accNumber);
            }
        } catch (SQLException e) {
            System.out.println("Database connection failure.");
            BankApp.mainLogger.log(Level.SEVERE, "db connection error", e);
        }

        return newWithdrawal;
    }
}
