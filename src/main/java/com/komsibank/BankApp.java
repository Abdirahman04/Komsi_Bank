package com.komsibank;

import com.komsibank.account.CreateAccount;
import com.komsibank.account.Login;

import java.util.Scanner;

public class BankApp {
    public static void main(String[] args) {
        System.out.println(">>>>    WELCOME TO KOMSI BANK    <<<<");

        menu();
    }

    public static void menu() {
        Scanner sc = new Scanner(System.in);

        System.out.println(">>>>>     BANK APP     <<<<<\n");

        System.out.println("1. <Login>");
        System.out.println("2. <Create New Account>");
        System.out.println("3. <Help>");
        System.out.println("4. <Exit>");

        System.out.print(">>>>>>>>>>    ");
        String ans = sc.nextLine();

        if (ans.equals("1")) Login.login();
        if (ans.equals("2")) CreateAccount.newAccount();
        if (ans.equals("3")) Help.help();
        if (ans.equals("4")) System.exit(0);
        else menu();
    }

}
