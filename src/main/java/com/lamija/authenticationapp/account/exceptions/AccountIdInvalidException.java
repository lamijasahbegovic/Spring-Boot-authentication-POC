package com.lamija.authenticationapp.account.exceptions;

public class AccountIdInvalidException extends RuntimeException {

    public AccountIdInvalidException() {
        super("Account ID is not valid.");
    }
}
