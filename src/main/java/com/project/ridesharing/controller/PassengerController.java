package com.project.ridesharing.controller;

import com.project.ridesharing.model.Passenger;
import com.project.ridesharing.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PassengerController {

    @Autowired
    PassengerService passengerService;

    // save a passenger
    @PostMapping("/passenger/save")
    public Passenger save(@Validated @RequestBody Passenger passenger) {
        return passengerService.save(passenger);
    }

    // get all passengers
    @GetMapping("/passenger/all")
    public List<Passenger> findAll() {
        return passengerService.findAll();
    }

    // get a passenger by passenger_id
    @GetMapping("/passenger/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable(value = "id") Integer passenger_id) {
        Passenger passenger = passengerService.findById(passenger_id);
        if (passenger == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(passenger);
    }

    // update a passenger by passenger_id
    @PutMapping("/passenger/update/{id}")
    public ResponseEntity<Passenger> updatePassengerById(@PathVariable(value = "id") Integer passenger_id, @Validated @RequestBody Passenger new_passenger) {
        Passenger passenger = passengerService.findById(passenger_id);
        if (passenger == null) {
            return ResponseEntity.notFound().build();
        }
        passenger.setName(new_passenger.getName());
        passenger.setSurname(new_passenger.getSurname());
        passenger.setEmail(new_passenger.getEmail());
        passenger.setPhone_number(new_passenger.getPhone_number());
        passenger.setTc_number(new_passenger.getTc_number());
        passenger.setAddress(new_passenger.getAddress());
        passenger.setGender(new_passenger.getGender());
        Passenger updated_passenger = passengerService.save(passenger);
        return ResponseEntity.ok().body(updated_passenger);
    }

    // delete a passenger by passenger_id
    @DeleteMapping("/passenger/delete/{id}")
    public ResponseEntity<Passenger> deletePassengerById(@PathVariable(value = "id") Integer passenger_id) {
        Passenger passenger = passengerService.findById(passenger_id);
        if (passenger == null) {
            return ResponseEntity.notFound().build();
        }
        passengerService.delete(passenger);
        return ResponseEntity.ok().build();
    }

}
