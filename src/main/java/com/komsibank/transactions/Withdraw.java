package com.komsibank.transactions;

import com.komsibank.Validations;
import com.komsibank.account.HomePage;
import com.komsibank.dbconnection.PostgresConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Withdraw {
    public static void withdraw(String accNumber) {
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
            System.out.println("Money withdrawn successfully");
            HomePage.home(accNumber);
        } catch (SQLException e) {
            System.out.println("Database connection failure.");
            e.printStackTrace();
        }
    }
    public static double checkWithdrawal(String withdrawal,String accNumber,PostgresConnection conn) {
        if(!Validations.isNumber(withdrawal)) {
            System.out.println("Invalid input!!");
            withdraw(accNumber);
        }
        double newWithdrawal = Double.parseDouble(withdrawal);
        if(newWithdrawal <= 0) {
            System.out.println("Withdrawal should be more than 0!!");
            withdraw(accNumber);
        }
        try (Connection connection = conn.connectToDatabase(conn.getUrl(), conn.getUser(), conn.getPassword())) {
            if(conn.isBalanceMore(connection,accNumber,newWithdrawal)) {
                System.out.println("You don't have enough funds!!");
                withdraw(accNumber);
            }
        } catch (SQLException e) {
            System.out.println("Database connection failure.");
            e.printStackTrace();
        }

        return newWithdrawal;
    }
}
