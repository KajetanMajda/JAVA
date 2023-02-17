package com.example.SmallCarCollection.car;

import com.example.SmallCarCollection.car.Exception.WrongIdException;
import com.example.SmallCarCollection.car.Exception.WrongMakeException;
import com.example.SmallCarCollection.car.Exception.WrongRegistrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CarRestController {
    CarService carService;

    @Autowired
    public CarRestController(CarService carService) {
        this.carService = carService;
    }


    @GetMapping("car/id/{id}") //na jaki adres ma wejsc zeby ta funkcja byla wywoalna :D
    public ResponseEntity<Car> getCarById(@PathVariable("id") Long id){
        var car = carService.findById(id);
        if(car.isPresent()){
            return new ResponseEntity<>(car.get(), HttpStatus.OK);
        } else {
            throw new WrongIdException("Car id not found");
        }

    }

    @GetMapping("car/make/{make}")
    public ResponseEntity<List<Car>> getCarByMake(@PathVariable("make") String make){
        List<Car> carList = carService.findByMake(make);
        boolean hasThisMake = carList.stream()
                .anyMatch((c) -> c.getMake().equals(make));
        if(hasThisMake){
            return new ResponseEntity<>(carList, HttpStatus.OK);
        } else {
            throw new WrongMakeException("Car make not found");
        }

    }

    @GetMapping("car/registration/{registration}")
    public ResponseEntity<Car> getCarByRegistration(@PathVariable("registration") String registration) {
        Car carByReg = carService.findByRegistration(registration);
        if (carByReg == null){
            throw new WrongRegistrationException("Car registration not found");
        }
        if (carByReg.getRegistration().equals(registration)) {
            return ResponseEntity.ok(carByReg);
        } else {
            throw new WrongRegistrationException("Car registration not found");
        }

    }

    @PostMapping("car/add")
    public ResponseEntity<Car> postCar(@RequestBody Car car){
        Car savedNewCar = carService.getCarRepository().save(car);
        if(savedNewCar.getRegistration().length() < 5){
            throw new WrongRegistrationException("Car registration is too short");
        } else if (savedNewCar.getMake().equals("")){
            throw new WrongMakeException("Make can't be null");
        } else {
            return new ResponseEntity<>(savedNewCar, HttpStatus.CREATED);
        }

    }

//    @PatchMapping("car/{id}/{make}/{registration}")
//    public ResponseEntity<Car> updateCar(@PathVariable("id") Long id, @PathVariable("make") String make, @PathVariable("registration") String registration){
//        Optional<Car> carById = carService.findById(id);
//
//        if (carById.isPresent()){
//            //return carById;
//        } else {
//            throw new WrongIdException("Car id doesn't exist");
//        }
//
//    }

    @DeleteMapping("car/remove/{id}")
    public ResponseEntity<Car> deleteCar(@PathVariable(value = "id") Long id){
        Optional<Car> carById = carService.findById(id);

        if(carById.isPresent()){
            carService.getCarRepository().delete(carById.get());
            return new ResponseEntity<>(carById.get(), HttpStatus.OK);
        } else {
            throw new WrongIdException("Car id doesn't exist");
        }

    }



    }
