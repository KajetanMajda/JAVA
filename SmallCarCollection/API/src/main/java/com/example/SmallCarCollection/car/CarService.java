package com.example.SmallCarCollection.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
        this.carRepository.save(new Car("Audi", "GTC 420"));
        this.carRepository.save(new Car("BMW", "GTC 2137"));
    }

    public CarRepository getCarRepository() {
        return carRepository;
    }

    public void setCarRepository(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Optional<Car> findById(Long id){
        return this.getCarRepository().findById(id);
    }

    public List<Car> findByMake(String make){
        return this.getCarRepository().findByMake(make);
    }

    public Car findByRegistration(String registration) {
        return this.getCarRepository().findByRegistration(registration);
    }

    public Iterable<Car> findAll() {return carRepository.findAll();}

}
