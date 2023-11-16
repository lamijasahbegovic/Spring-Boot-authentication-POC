package com.lamija.authenticationapp.authentication.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationExpiredException extends AuthenticationException {

    public AuthenticationExpiredException() {
        super("Authentication for this account has expired.");
    }
}
