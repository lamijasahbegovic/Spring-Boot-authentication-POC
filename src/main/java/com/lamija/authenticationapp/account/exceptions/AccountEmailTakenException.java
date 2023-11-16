package com.lamija.authenticationapp.account.exceptions;

public class AccountEmailTakenException extends RuntimeException {

    public AccountEmailTakenException() {
        super("An account with that email already exists.");
    }
}
