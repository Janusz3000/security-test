package com.example.demo.exception;


public class UserNotActivatedException extends RuntimeException {

    public UserNotActivatedException(String message) {
        super(message);
    }

}
