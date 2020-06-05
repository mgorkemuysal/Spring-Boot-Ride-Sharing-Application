package com.project.ridesharing.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.ridesharing.model.Trip;
import com.project.ridesharing.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TripController {

    @Autowired
    TripService tripService;

    // save a trip
    @PostMapping("/trip/save")
    public Trip save(@Validated @RequestBody Trip trip) {
        return tripService.save(trip);
    }

    // get all trips
    @GetMapping("/trip/all")
    public List<Trip> findAll() {
        return tripService.findAll();
    }

    // get a trip by trip_id
    @GetMapping("/trip/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable(value = "id") Integer trip_id) {
        Trip trip = tripService.findById(trip_id);
        if (trip == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(trip);
    }

    // get trips by driver_id
    @GetMapping("/trip/driver/{id}")
    public List<Trip> findTripByDriverId(@PathVariable(value = "id") Integer driver_id) {
        return tripService.findTripByDriverId(driver_id);
    }

    // get trips by departure location
    @GetMapping("/trip/departure/{departure}")
    public List<Trip> findTripByDeparture(@PathVariable(value = "departure") String departure) {
        return tripService.findTripByDeparture(departure);
    }

    // get trips by arrival location
    @GetMapping("/trip/arrival/{arrival}")
    public List<Trip> findTripByArrival(@PathVariable(value = "arrival") String arrival) {
        return tripService.findTripByArrival(arrival);
    }

    // get trips by departure and arrival locations
    @GetMapping("/trip/location/{departure}-{arrival}")
    public List<Trip> findTripByDepartureAndArrival(@PathVariable(value = "departure") String departure,
                                                    @PathVariable(value = "arrival") String arrival){
        return tripService.findTripDepartureAndArrival(departure, arrival);
    }

    // get trips by departure, arrival and date
    @GetMapping("/trip/date/{departure}-{arrival}")
    public List<Trip> findTripByDepartureAndArrivalAndDate(
            @PathVariable(value = "departure") String departure,
            @PathVariable(value = "arrival") String arrival,
            @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return tripService.findTripDepartureAndArrivalAndDate(departure, arrival, date);
    }

    // update a trip by trip_id
    @PutMapping("/trip/update/{id}")
    public ResponseEntity<Trip> updateTripById(@PathVariable(value = "id") Integer trip_id, @Validated @RequestBody Trip new_trip) {
        Trip trip = tripService.findById(trip_id);
        if (trip == null) {
            return ResponseEntity.notFound().build();
        }
        trip.setDeparture(new_trip.getDeparture());
        trip.setArrival(new_trip.getArrival());
        trip.setDate(new_trip.getDate());
        trip.setTime(new_trip.getTime());
        trip.setPrice(new_trip.getPrice());
        trip.setSeat_number(new_trip.getSeat_number());
        trip.setDiscount(new_trip.getDiscount());
        trip.setTotal_distance(new_trip.getTotal_distance());
        trip.setTotal_duration(new_trip.getTotal_duration());
        trip.setActive(new_trip.isActive());
        trip.setBooked(new_trip.isBooked());
        Trip updated_trip = tripService.save(new_trip);
        return ResponseEntity.ok().body(updated_trip);
    }

    // delete a trip by trip_id
    @DeleteMapping("/trip/delete/{id}")
    public ResponseEntity<Trip> deleteTripById(@PathVariable(value = "id") Integer trip_id) {
        Trip trip = tripService.findById(trip_id);
        if (trip == null) {
            ResponseEntity.notFound().build();
        }
        tripService.delete(trip);
        return ResponseEntity.ok().build();
    }

}
