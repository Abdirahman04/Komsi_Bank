package com.komsibank.account;

import com.komsibank.dbconnection.PostgresConnection;
import com.komsibank.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class CreateAccount {
    public static void newAccount() {
        Scanner sc = new Scanner(System.in);
        System.out.println(">>>>>     CREATE NEW ACCOUNT     <<<<<");
        System.out.print("First name: ");
        String fname = sc.nextLine();
        System.out.print("Last name: ");
        String lname = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Account number: ");
        String accountNumber = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        String fullName = fname + " " + lname;

        PostgresConnection conn = new PostgresConnection();
        try (Connection connection = conn.connectToDatabase(conn.getUrl(), conn.getUser(), conn.getPassword())) {
            conn.insertUserData(connection, accountNumber, fname, lname, email, 0, pass);
            System.out.println("User added successfully");
        } catch (SQLException e) {
            System.out.println("Database connection failure.");
            e.printStackTrace();
        }

        HomePage.home(accountNumber);
    }
}
