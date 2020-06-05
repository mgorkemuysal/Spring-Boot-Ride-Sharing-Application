package com.project.ridesharing.service;

import com.project.ridesharing.model.Driver;
import com.project.ridesharing.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    @Autowired
    DriverRepository driverRepository;

    // save a driver
    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }

    // get all drivers
    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    // get a driver by driver_id
    public Driver findById(Integer driver_id) {
        return driverRepository.findById(driver_id).orElse(null);
    }

    // get all drivers by city_id
    public List<Driver> findDriverByCityId(Integer city_id) {
        return driverRepository.findDriverByCityId(city_id);
    }

    // get a driver by car_id
    public List<Driver> findDriverByCarId(Integer car_id) {
        return driverRepository.findDriverByCarId(car_id);
    }

    // delete a driver by driver object
    public void delete(Driver driver) {
        driverRepository.delete(driver);
    }

}
