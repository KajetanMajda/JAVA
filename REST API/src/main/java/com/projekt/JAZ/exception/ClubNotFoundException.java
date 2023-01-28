package com.projekt.JAZ.exception;

public class ClubNotFoundException extends RuntimeException{

    public ClubNotFoundException(Long id) {

        super("The Club id '" + id + "' does not exist in our database. Try again");
    }
}
