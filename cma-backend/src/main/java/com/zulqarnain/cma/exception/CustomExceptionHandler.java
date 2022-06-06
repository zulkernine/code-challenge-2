package com.zulqarnain.cma.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {ApiException.class})
    ResponseEntity handleApiException(ApiException e){
        ErrorResponseBody responseBody = new ErrorResponseBody(e.getMessage(),
                e.getStatus(), LocalDateTime.now());

        return new ResponseEntity(responseBody,responseBody.getHttpStatus());
    }
}
