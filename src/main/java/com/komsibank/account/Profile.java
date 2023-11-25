package com.komsibank.account;

import com.komsibank.dbconnection.PostgresConnection;
import com.komsibank.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Profile {
    public static void profile(String accNumber) {
        System.out.println(">>>>>     PROFILE     <<<<<\n");

        PostgresConnection conn = new PostgresConnection();
        try (Connection connection = conn.connectToDatabase(conn.getUrl(), conn.getUser(), conn.getPassword())) {
            User user = conn.getUser(connection,accNumber);
            System.out.println(">>  Name: " + user.getFname()+ " "+ user.getLname());
            System.out.println(">>  Email: " + user.getEmail());
            System.out.println(">>  Account number: " + accNumber);
            System.out.println(">>  Balance: " + user.getBalance());
        } catch (SQLException e) {
            System.out.println("Database connection failure.");
            e.printStackTrace();
        }

        System.out.println("1.  <View previous transactions>");
        System.out.println("2.  <Delete account>");
        System.out.println("3.  <Back>");

        Scanner sc = new Scanner(System.in);
        String ans = sc.nextLine();

        if(ans.equals("1")) PreviousTransactions.transactions(accNumber);
        else if(ans.equals("3")) HomePage.home(accNumber);
        else if(ans.equals("2")) {
            System.out.println("Are you sure you want to delete your account! [y/n]");
            System.out.print(">>>>    ");

            String deleteAns = sc.nextLine();

            if(deleteAns.equals("y")) {
                System.out.println("delete");
//                DeleteAccount.deleteThisAccount(accNumber);
//                System.out.println("Account deleted successfully");
//                BankApp.menu();
            }
        }
    }
}
