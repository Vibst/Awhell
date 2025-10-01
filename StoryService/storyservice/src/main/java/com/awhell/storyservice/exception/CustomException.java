package com.awhell.storyservice.exception;

import org.springframework.http.HttpStatus;

import org.springframework.http.HttpStatusCode;

public class CustomException extends RuntimeException {

    public  HttpStatus status;

    public CustomException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        // TODO Auto-generated method stub
        return super.getMessage();
    }

}
