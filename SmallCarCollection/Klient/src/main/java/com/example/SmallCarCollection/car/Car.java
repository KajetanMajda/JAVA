package com.example.SmallCarCollection.car;

public class Car {

    private Long id;
    private String make;
    private String registration;


    public Car(){}

    public Car(String make, String registration) {
        this.make = make;
        this.registration = registration;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
