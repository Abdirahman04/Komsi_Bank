package com.komsibank.account;

import com.komsibank.dbconnection.PostgresConnection;
import com.komsibank.model.User;

public class CreateAccount {
    public static void newAccount() {
        System.out.println(">>>>>     CREATE NEW ACCOUNT     <<<<<");
        User user = new User();

        PostgresConnection.connect();
    }
}
