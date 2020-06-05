package com.project.ridesharing.service;

import com.project.ridesharing.model.Passenger;
import com.project.ridesharing.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    @Autowired
    PassengerRepository passengerRepository;

    // save a passenger
    public Passenger save(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    // get all passengers
    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    // get a passenger by passenger_id
    public Passenger findById(Integer passenger_id) {
        return passengerRepository.findById(passenger_id).orElse(null);
    }

    // delete a passenger by passenger object
    public void delete(Passenger passenger) {
        passengerRepository.delete(passenger);
    }

}
