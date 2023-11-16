package com.lamija.authenticationapp.authentication.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationInvalidException extends AuthenticationException {

    public AuthenticationInvalidException() {
        super("The account cannot be authenticated.");
    }
}
