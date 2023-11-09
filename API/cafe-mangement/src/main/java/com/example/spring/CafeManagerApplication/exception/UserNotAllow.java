package com.example.spring.CafeManagerApplication.exception;

public class UserNotAllow extends RuntimeException{
    public UserNotAllow() {
        super();
    }

    public UserNotAllow(String message) {
        super(message);
    }
}
