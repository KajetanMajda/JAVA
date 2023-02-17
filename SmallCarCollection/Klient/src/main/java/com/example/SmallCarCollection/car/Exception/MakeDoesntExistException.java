package com.example.SmallCarCollection.car.Exception;

public class MakeDoesntExistException extends RuntimeException {

    public MakeDoesntExistException(String message) {
        super(message);
    }
}
