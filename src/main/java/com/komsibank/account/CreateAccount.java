package com.komsibank.account;

import com.komsibank.Validations;
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
        String emailInput = sc.nextLine();
        String email = checkEmail(emailInput);

        System.out.print("Account number: ");
        String accountNumberInput = sc.nextLine();
        String accountNumber = checkAccountNumber(accountNumberInput);

        System.out.print("Password: ");
        String passInput = sc.nextLine();
        String pass = checkPassword(passInput);

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
    private static String checkEmail(String email) {
        if(!Validations.isValidEmail(email)) {
            System.out.println("Not a valid email!!");
            newAccount();
        }

        return email;
    }
    private static String checkAccountNumber(String accNumber) {
        if(!Validations.isNumber(accNumber)) {
            System.out.println("Account number should be a number!!");
            newAccount();
        }

        if(accNumber.length() < 9 || accNumber.length() > 10) {
            System.out.println("Account number should be 9 to 10 digits long!!");
            newAccount();
        }

        return accNumber;
    }
    private static String checkPassword(String pass) {
        if(pass.length() < 4) {
            System.out.println("Password should be at least 4 characters long");
            newAccount();
        }

        if(pass.length() > 19) {
            System.out.println("Password should not exceed 20 characters");
            newAccount();
        }

        return pass;
    }
}
