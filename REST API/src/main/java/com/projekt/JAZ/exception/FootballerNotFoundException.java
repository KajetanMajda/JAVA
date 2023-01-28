package com.projekt.JAZ.exception;

public class FootballerNotFoundException extends RuntimeException{

    public FootballerNotFoundException(Long id) {

        super("The Footballer id '" + id + "' does not exist in our database. Try again");
    }


}
