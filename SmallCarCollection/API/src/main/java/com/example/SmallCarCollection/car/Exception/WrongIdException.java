package com.example.SmallCarCollection.car.Exception;

public class WrongIdException extends RuntimeException{

    public WrongIdException(String message){
        super(message);
    }
}
