package com.example.demo;

public class WrongIdException extends RuntimeException{

    public WrongIdException(String message){
        super(message);
    }
}
