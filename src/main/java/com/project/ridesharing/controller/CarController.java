package com.project.ridesharing.controller;

import com.project.ridesharing.model.Car;
import com.project.ridesharing.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    CarService carService;

    // save a car
    @PostMapping("/car/save")
    public Car save(@Validated @RequestBody Car car) {
        return carService.save(car);
    }

    // get all cars
    @GetMapping("/car/all")
    public List<Car> findAll() {
        return carService.findAll();
    }

    // get a car by car_id
    @GetMapping("/car/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable(value = "id") Integer car_id) {
        Car car = carService.findById(car_id);
        if (car == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(car);
    }

    // update a car by car_id
    @PutMapping("/car/update/{id}")
    public ResponseEntity<Car> updateCarById(@PathVariable(value = "id") Integer car_id, @Validated @RequestBody Car new_car) {
        Car car = carService.findById(car_id);
        if (car == null) {
            return ResponseEntity.notFound().build();
        }
        car.setBrand(new_car.getBrand());
        car.setModel(new_car.getModel());
        car.setFuel_type(new_car.getFuel_type());
        car.setCar_class(new_car.getCar_class());
        car.setAc(new_car.isAc());
        Car updated_car = carService.save(car);
        return ResponseEntity.ok().body(updated_car);
    }

    // delete a car by car_id
    @DeleteMapping("/car/delete/{id}")
    public ResponseEntity<Car> deleteCarById(@PathVariable(value = "id") Integer car_id) {
        Car car = carService.findById(car_id);
        if (car == null) {
            return ResponseEntity.notFound().build();
        }
        carService.delete(car);
        return ResponseEntity.ok().build();
    }

}
