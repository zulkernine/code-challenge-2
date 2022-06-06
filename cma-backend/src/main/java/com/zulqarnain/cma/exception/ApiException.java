package com.zulqarnain.cma.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException{
    private final HttpStatus status;

    public ApiException(String message){
        super(message);
        status = HttpStatus.BAD_REQUEST;
    }

    public ApiException(String message,HttpStatus status){
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
