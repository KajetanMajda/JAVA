package com.projekt.JAZ.exception;

public class LeagueNotFoundException extends RuntimeException{

    public LeagueNotFoundException(Long id) {

        super("The League id '" + id + "' does not exist in our database. Try again");
    }
}
