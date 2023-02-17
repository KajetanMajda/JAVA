package com.example.SmallCarCollection.car;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findByMake(String make);
    Car findByRegistration(String registration);
}
