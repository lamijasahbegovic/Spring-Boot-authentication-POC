package com.lamija.authenticationapp.account.exceptions;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException() {
        super("Account not found.");
    }
}
