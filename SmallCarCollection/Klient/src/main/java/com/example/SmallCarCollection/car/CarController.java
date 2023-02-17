package com.example.SmallCarCollection.car;

import com.example.SmallCarCollection.car.Exception.MakeDoesntExistException;
import com.example.SmallCarCollection.car.Exception.WrongIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CarController {

    CarService carService;

    @Autowired
    public CarController(CarService carService) {

        this.carService = carService;
    }


    @GetMapping("car/id/{id}")
    public String getCarById(@PathVariable("id") Long id, Model model) throws Exception{
        Car car = carService.findByCarId(id);
        if (car != null) {
            model.addAttribute("car", car);
            return "car";
        } else {
            throw new WrongIdException("Car Id is not found");
        }

    }

@GetMapping("car/make/{make}")
public String getCarByMake(@PathVariable("make") String make, Model model) throws  Exception{
    Car car = carService.findByCarMake(make);
    if (car != null) {
        model.addAttribute("car", car);
        return "car";
    } else {
        throw new MakeDoesntExistException("Car make not found");
    }


}
//
//    @GetMapping("car/plate/{registration}")
//    public ResponseEntity<Car> getCarByRegistration(@PathVariable("registration") String registration) {
//        Car carByPlate = restTemplate.findByCarRegistration(registration);
//
//        if (carByPlate != null) {
//            return new ResponseEntity<>(carByPlate, HttpStatus.OK);
//        } else {
//            throw new PlateDoesntExistException("Car registration not found");
//        }
//
//    }
//
//    @PostMapping("car/add")
//    public ResponseEntity<Car> postCar(@RequestBody Car car) {
//        Car carLengthOfPlate = restTemplate.addingNewCarToOurDataBase(car);
//
//
//        if (carLengthOfPlate.getMake().equals("")) {
//            throw new CarMakeIsNull("Make cannot be null");
//        } else if (carLengthOfPlate.getRegistration().length() <= 8) {
//
//            return new ResponseEntity<>(carLengthOfPlate, HttpStatus.CREATED);
//        } else {
//            throw new CarMaxLengthOfPlateIs8WithSpace("Wrong registration format");
//        }
//    }
//
//
//    @DeleteMapping("car/delete/{id}")
//    public ResponseEntity<Car> deleteCar(@PathVariable("id") Long id) {
//        Optional<Car> deletedCar = restTemplate.findByCarId(id);
//
//        if(deletedCar.isPresent()){
//            carService.getCarRepository().delete(deletedCar.get());
//            return new ResponseEntity<>(deletedCar.get(), HttpStatus.OK);
//        }
//        else {
//            throw new CarDeletingException("Car id does not exist");
//        }
//
//
//    }
//
//    @PatchMapping("car/patch/{id}/{make}")
//    public ResponseEntity<Car> patchCar(@PathVariable("id") Long id, @PathVariable("make") String make){
//
//        Optional<Car> patchCar = restTemplate.findByCarId(id);
//
//        if(patchCar.isPresent()){
//            try {
//                Car car = restTemplate.getCarRepository().findById(id).get();
//                car.setMake(make);
//                return new ResponseEntity<>(restTemplate.getCarRepository().save(car), HttpStatus.OK);
//            } catch (Exception e) {
//                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//        else {
//            throw new CarPatchException("Car id does not exist");
//        }
//
//    }


}
