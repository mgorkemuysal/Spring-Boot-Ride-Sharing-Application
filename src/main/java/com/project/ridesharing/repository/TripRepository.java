package com.project.ridesharing.repository;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.ridesharing.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
    List<Trip> findTripByDeparture(String departure);
    List<Trip> findTripByArrival(String arrival);
    List<Trip> findTripByDepartureAndArrival(String departure, String arrival);
    List<Trip> findTripByDepartureAndArrivalAndDate(String departure, String arrival, @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date);
    List<Trip> findTripByDriverId(Integer driver_id);
}
