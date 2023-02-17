package com.example.SmallCarCollection.car;

import com.example.SmallCarCollection.car.Exception.WrongIdException;
import com.example.SmallCarCollection.car.Exception.WrongMakeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CarService {

    private final String API_URL = "http://localhost:8081/";
    RestTemplate restTemplate;


    @Autowired // wez to sam mi uzupełnij ja tak mowie sobie do gradle i ez
    public CarService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RestTemplate getCarRepository() {
        return restTemplate;
    }

    public void setCarRepository(RestTemplate carRepository) {
        this.restTemplate = carRepository;
    }

    public Car findByCarId(Long id) {
        ResponseEntity<Car> response = restTemplate.getForEntity(API_URL + "car/id/" + id, Car.class);
        if(response.getBody() != null)
            return response.getBody();
        else {
            throw new WrongIdException("message");
        }
    }

    public Car findByCarMake(String make) {
        ResponseEntity<Car> response = restTemplate.getForEntity(API_URL+"car/make/" + make, Car.class);
        if(response.getBody() != null)
            return response.getBody();
        else {
            throw new WrongMakeException("message");
        }
    }
//
//    public Car findByCarRegistration(String Registration) {
//        return restTemplate.findByRegistration(Registration);
//    }
//
//    public Car addingNewCarToOurDataBase(Car car) {
//        return this.restTemplate.save(car);
//    }

}