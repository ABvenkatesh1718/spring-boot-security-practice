package com.venkatesh.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String>handleUserExists(UserAlreadyExistsException userAlreadyExistsException){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(userAlreadyExistsException.getMessage());
    }
}
