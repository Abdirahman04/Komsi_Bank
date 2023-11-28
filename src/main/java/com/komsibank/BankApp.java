package com.komsibank;

import com.komsibank.account.CreateAccount;
import com.komsibank.account.Login;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.*;

public class BankApp {
    public static final Logger mainLogger = Logger.getLogger("com.konsibank");

    static {
        try {
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            for (Handler handler : handlers) {
                if (handler instanceof ConsoleHandler) {
                    rootLogger.removeHandler(handler);
                }
            }
            Handler fileHandler = new FileHandler("application.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            mainLogger.addHandler(fileHandler);
            mainLogger.setLevel(Level.ALL);
        } catch (IOException ex) {
            mainLogger.log(Level.SEVERE,"Exception occurred",ex);
        }
    }
    public static void main(String[] args) {
        mainLogger.info("application has started");
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

        if (ans.equals("1")) {
            mainLogger.info("login selected");
            Login.login();
        }
        if (ans.equals("2")) {
            mainLogger.info("create account selected");
            CreateAccount.newAccount();
        }
        if (ans.equals("3")) {
            mainLogger.info("help selected");
            Help.help();
        }
        if (ans.equals("4")) {
            mainLogger.info("application has been stopped");
            System.exit(0);
        }
        else menu();
    }

}
