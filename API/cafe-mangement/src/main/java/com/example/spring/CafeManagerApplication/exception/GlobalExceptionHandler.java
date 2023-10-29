package com.example.spring.CafeManagerApplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ProductExistException exception){
        ErrorResponse e = new ErrorResponse();
        e.setStatus(HttpStatus.BAD_REQUEST.value());
        e.setMessage(exception.getMessage());
        e.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
    }
}
