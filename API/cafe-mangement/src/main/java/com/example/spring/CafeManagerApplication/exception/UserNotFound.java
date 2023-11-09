package com.example.spring.CafeManagerApplication.exception;

public class UserNotFound extends RuntimeException{
    public UserNotFound() {
        super();
    }

    public UserNotFound(String message) {
        super(message);
    }
}
