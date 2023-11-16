package com.lamija.authenticationapp.account.exceptions;

public class AccountUnauthorizedException extends RuntimeException {

    public AccountUnauthorizedException() {
        super("Account not authorized.");
    }
}
