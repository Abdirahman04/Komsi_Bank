package com.komsibank.account;

import com.komsibank.BankApp;
import com.komsibank.dbconnection.PostgresConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {
    public static void login() {
        Scanner sc = new Scanner(System.in);

        System.out.println(">>>>>     LOGIN     <<<<<");

        System.out.print("Account number: ");
        String accNumber = sc.nextLine();

        System.out.print("Password: ");
        String pass = sc.nextLine();

        PostgresConnection conn = new PostgresConnection();

        try (Connection connection = conn.connectToDatabase(conn.getUrl(), conn.getUser(), conn.getPassword())) {
            boolean userExists = conn.doesUserExist(connection, accNumber, pass);

            if (userExists) {
                System.out.println("Login successful!");
                HomePage.home(accNumber);
            }
            else {
                System.out.println("User account does not exist!");
                BankApp.menu();
            }
        } catch (SQLException e) {
            System.out.println("Database connection failure.");
            e.printStackTrace();
        }
    }
}
