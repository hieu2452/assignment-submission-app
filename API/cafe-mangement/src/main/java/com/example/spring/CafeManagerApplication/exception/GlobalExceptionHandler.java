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

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ProductNotFoundException exception){
        ErrorResponse e = new ErrorResponse();
        e.setStatus(HttpStatus.NOT_FOUND.value());
        e.setMessage(exception.getMessage());
        e.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UserNotAllow exception){
        ErrorResponse e = new ErrorResponse();
        e.setStatus(HttpStatus.FORBIDDEN.value());
        e.setMessage(exception.getMessage());
        e.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(e,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UserNotFound exception){
        ErrorResponse e = new ErrorResponse();
        e.setStatus(HttpStatus.NOT_FOUND.value());
        e.setMessage(exception.getMessage());
        e.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
    }
}
