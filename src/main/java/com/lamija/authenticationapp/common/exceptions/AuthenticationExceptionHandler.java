package com.lamija.authenticationapp.common.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamija.authenticationapp.authentication.exceptions.AuthenticationExpiredException;
import com.lamija.authenticationapp.authentication.exceptions.AuthenticationInvalidException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {

    @ExceptionHandler({AuthenticationInvalidException.class, AuthenticationExpiredException.class})
    public void handleTokenExceptions(
            HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        generateAuthenticationErrorResponse(response, authException);
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException {
        generateAuthenticationErrorResponse(response, authException);
    }

    public void generateAuthenticationErrorResponse(
            HttpServletResponse response, AuthenticationException authenticationException)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter()
                .write(
                        new ObjectMapper()
                                .writeValueAsString(
                                        new GlobalError(
                                                authenticationException.getLocalizedMessage())));
        response.flushBuffer();
    }
}
