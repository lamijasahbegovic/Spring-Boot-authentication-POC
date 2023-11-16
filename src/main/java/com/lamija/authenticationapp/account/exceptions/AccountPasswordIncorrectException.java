package com.lamija.authenticationapp.account.exceptions;

public class AccountPasswordIncorrectException extends RuntimeException {

    public AccountPasswordIncorrectException() {
        super("The password is incorrect for that account.");
    }
}
