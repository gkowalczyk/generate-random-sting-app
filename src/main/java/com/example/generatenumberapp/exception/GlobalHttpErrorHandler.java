package com.example.generatenumberapp.exception;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PermutationException.class)
    public ResponseEntity<Object> handleTaskNotFoundException(PermutationException exception) {
        return new ResponseEntity<>("Change the number of combinations or char Strings", HttpStatus.BAD_REQUEST);
    }
}
