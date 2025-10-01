package com.awhell.storyservice.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRunTimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("RunTime Exception In Globally" + ex.getMessage());

    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponseModel> handleCustomizeHandler(CustomException ex) {

        ExceptionResponseModel response = new ExceptionResponseModel(new Date(), ex.getStatus(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    
}
