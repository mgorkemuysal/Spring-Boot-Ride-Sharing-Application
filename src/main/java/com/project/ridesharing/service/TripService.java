package com.project.ridesharing.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.ridesharing.model.Trip;
import com.project.ridesharing.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TripService {

    @Autowired
    TripRepository tripRepository;

    // save a trip
    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }

    // get all trips
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    // get a trip by trip_id
    public Trip findById(Integer trip_id) {
        return tripRepository.findById(trip_id).orElse(null);
    }

    // get all trips by driver_id
    public List<Trip> findTripByDriverId(Integer driver_id) {
        return tripRepository.findTripByDriverId(driver_id);
    }

    // get all trips by departure location
    public List<Trip> findTripByDeparture(String departure) {
        return tripRepository.findTripByDeparture(departure);
    }

    // get all trips by arrival location
    public List<Trip> findTripByArrival(String arrival) {
        return tripRepository.findTripByArrival(arrival);
    }

    // get all trips by departure and arrival location
    public List<Trip> findTripDepartureAndArrival(String departure, String arrival) {
        return tripRepository.findTripByDepartureAndArrival(departure, arrival);
    }

    // get all trips by departure, arrival and date
    public List<Trip> findTripDepartureAndArrivalAndDate(String departure, String arrival, @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return tripRepository.findTripByDepartureAndArrivalAndDate(departure, arrival, date);
    }

    // delete a trip by trip object
    public void delete(Trip trip) {
        tripRepository.delete(trip);
    }

}
