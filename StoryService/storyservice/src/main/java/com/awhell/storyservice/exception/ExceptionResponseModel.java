package com.awhell.storyservice.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponseModel {
    private Date timeStamp;
    private String message;
    private HttpStatus status;

    public ExceptionResponseModel(Date date, org.springframework.http.HttpStatus status, String msg) {
        this.timeStamp = date;
        this.message = msg;
        this.status = status;

    }
}
