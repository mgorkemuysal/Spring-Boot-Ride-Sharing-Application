package com.project.ridesharing.controller;

import com.project.ridesharing.model.Driver;
import com.project.ridesharing.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DriverController {

    @Autowired
    DriverService driverService;

    // save a driver
    @PostMapping("/driver/save")
    public Driver save(@Validated @RequestBody Driver driver) {
        return driverService.save(driver);
    }

    // get all drivers
    @GetMapping("/driver/all")
    public List<Driver> findAll() {
        return driverService.findAll();
    }

    // get a driver by driver_id
    @GetMapping("/driver/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable(value = "id") Integer driver_id) {
        Driver driver = driverService.findById(driver_id);
        if (driver == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(driver);
    }

    // get drivers by city_id
    @GetMapping("/driver/city/{id}")
    public List<Driver> findDriverByCityId(@PathVariable(value = "id") Integer city_id) {
        return driverService.findDriverByCityId(city_id);
    }

    // get a driver by car_id
    @GetMapping("/driver/car/{id}")
    public List<Driver> findDriverByCarId(@PathVariable(value = "id") Integer car_id) {
        return driverService.findDriverByCarId(car_id);
    }


    // update a driver by driver_id
    @PutMapping("/driver/update/{id}")
    public ResponseEntity<Driver> updateDriverById(@PathVariable(value = "id") Integer driver_id, @Validated @RequestBody Driver new_driver) {
        Driver driver = driverService.findById(driver_id);
        if (driver == null) {
            return ResponseEntity.notFound().build();
        }
        driver.setName(new_driver.getName());
        driver.setSurname(new_driver.getSurname());
        driver.setEmail(new_driver.getEmail());
        driver.setPhone_number(new_driver.getPhone_number());
        driver.setTc_number(new_driver.getTc_number());
        driver.setAddress(new_driver.getAddress());
        driver.setGender(new_driver.getGender());
        Driver updated_driver = driverService.save(driver);
        return ResponseEntity.ok().body(updated_driver);
    }

    // delete a driver by driver_id
    @DeleteMapping("/driver/delete/{id}")
    public ResponseEntity<Driver> deleteDriverById(@PathVariable(value = "id") Integer driver_id) {
        Driver driver = driverService.findById(driver_id);
        if (driver == null) {
            ResponseEntity.notFound().build();
        }
        driverService.delete(driver);
        return ResponseEntity.ok().build();
    }

}
