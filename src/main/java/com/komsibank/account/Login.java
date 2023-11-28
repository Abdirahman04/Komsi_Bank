package com.komsibank.account;

import com.komsibank.BankApp;
import com.komsibank.dbconnection.PostgresConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;

public class Login {
    public static void login() {
        Scanner sc = new Scanner(System.in);

        System.out.println(">>>>>     LOGIN     <<<<<");

        System.out.print("Account number: ");
        String accNumber = sc.nextLine();
        BankApp.mainLogger.info("account number inputted");

        System.out.print("Password: ");
        String pass = sc.nextLine();
        BankApp.mainLogger.info("password inputted");

        PostgresConnection conn = new PostgresConnection();

        try (Connection connection = conn.connectToDatabase(conn.getUrl(), conn.getUser(), conn.getPassword())) {
            boolean userExists = conn.doesUserExist(connection, accNumber, pass);

            if (userExists) {
                BankApp.mainLogger.info("login successful");
                BankApp.mainLogger.info("user: " + accNumber + " logged in");
                BankApp.accountLogger.info("user: " + accNumber + " logged in");
                System.out.println("Login successful!");
                HomePage.home(accNumber);
            }
            else {
                BankApp.mainLogger.warning("login error");
                System.out.println("User account does not exist!");
                BankApp.menu();
            }
        } catch (SQLException e) {
            System.out.println("Database connection failure.");
            BankApp.mainLogger.log(Level.SEVERE, "db connection error", e);
        }
    }
}
