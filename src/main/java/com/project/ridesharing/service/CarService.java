package com.project.ridesharing.service;

import com.project.ridesharing.model.Car;
import com.project.ridesharing.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    // save a car
    public Car save(Car car) {
        return carRepository.save(car);
    }

    // get all cars
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    // get a car by car_id
    public Car findById(Integer car_id) {
        return carRepository.findById(car_id).orElse(null);
    }

    // delete a car by car object
    public void delete(Car car) {
        carRepository.delete(car);
    }

}
