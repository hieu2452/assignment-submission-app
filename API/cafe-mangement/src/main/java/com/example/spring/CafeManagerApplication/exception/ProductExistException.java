package com.example.spring.CafeManagerApplication.exception;

public class ProductExistException extends RuntimeException{
    public ProductExistException() {
        super();
    }

    public ProductExistException(String message) {
        super(message);
    }

    public ProductExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
