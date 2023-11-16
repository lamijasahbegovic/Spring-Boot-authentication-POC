package com.lamija.authenticationapp.common.exceptions;

import com.lamija.authenticationapp.account.exceptions.AccountEmailTakenException;
import com.lamija.authenticationapp.account.exceptions.AccountIdInvalidException;
import com.lamija.authenticationapp.account.exceptions.AccountNotFoundException;
import com.lamija.authenticationapp.account.exceptions.AccountPasswordIncorrectException;
import com.lamija.authenticationapp.account.exceptions.AccountUnauthorizedException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<GlobalError> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> messages = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            messages.add(violation.getMessage());
        }
        GlobalError globalError = new GlobalError(messages);
        return new ResponseEntity<>(globalError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<GlobalError> handleConstraintViolation(
            MethodArgumentNotValidException ex) {
        List<String> messages = new ArrayList<>();
        for (ObjectError violation : ex.getAllErrors()) {
            messages.add(violation.getDefaultMessage());
        }

        return new ResponseEntity<>(new GlobalError(messages), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, NumberFormatException.class})
    public ResponseEntity<GlobalError> handleInvalidPathVariable(RuntimeException ex) {
        return new ResponseEntity<>(
                new GlobalError("Invalid type assigned to a path variable."),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<GlobalError> handleMissingRequestParameter(
            MissingServletRequestParameterException ex) {
        return new ResponseEntity<>(
                new GlobalError("Missing request parameter."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<GlobalError> handleBadRequest(RuntimeException ex) {
        return new ResponseEntity<>(
                new GlobalError(ex.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccountUnauthorizedException.class})
    public ResponseEntity<GlobalError> handleUnauthorised(RuntimeException ex) {
        return new ResponseEntity<>(
                new GlobalError(ex.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({AccountNotFoundException.class})
    public ResponseEntity<GlobalError> handleNotFound(RuntimeException ex) {
        return new ResponseEntity<>(
                new GlobalError(ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
        AccountIdInvalidException.class,
        AccountPasswordIncorrectException.class,
        AccountEmailTakenException.class
    })
    public ResponseEntity<GlobalError> handleNotAcceptable(RuntimeException ex) {
        return new ResponseEntity<>(
                new GlobalError(ex.getLocalizedMessage()), HttpStatus.NOT_ACCEPTABLE);
    }
}
