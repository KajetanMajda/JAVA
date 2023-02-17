package com.example.demo;

public class PlateDoesntExistException extends RuntimeException {

    public PlateDoesntExistException(String message) {
        super(message);
    }
}
