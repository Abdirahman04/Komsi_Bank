package com.komsibank.account;

import com.komsibank.BankApp;
import com.komsibank.transactions.Deposit;
import com.komsibank.transactions.Send;
import com.komsibank.transactions.Withdraw;

import java.util.Scanner;

public class HomePage {
    public static void home(String accNumber) {
        BankApp.mainLogger.info("home page loaded");

        Scanner sc = new Scanner(System.in);

        System.out.println("     1. <Profile>");
        System.out.println("     2. <Deposit money>");
        System.out.println("     3. <Withdraw money>");
        System.out.println("     4. <Send money>");
        System.out.println("     5. <Go back to main menu>");
        System.out.print(">>>>>>>>>>    ");

        String ans = sc.nextLine();

        if (ans.equals("1")) {
            BankApp.mainLogger.info("profile selected");
            Profile.profile(accNumber);
        }
        if (ans.equals("2")) {
            BankApp.mainLogger.info("deposit selected");
            Deposit.deposit(accNumber);
        }
        if (ans.equals("3")) {
            BankApp.mainLogger.info("withdraw selected");
            Withdraw.withdraw(accNumber);
        }
        if (ans.equals("4")) {
            BankApp.mainLogger.info("send selected");
            Send.send(accNumber);
        }
        if (ans.equals("5")) {
            BankApp.mainLogger.info("user: " + accNumber + " logged out");
            BankApp.menu();
        }
        else home(accNumber);
    }
}
