package com.project.ridesharing.service;

import com.project.ridesharing.model.Driver;
import com.project.ridesharing.model.Trip;
import com.project.ridesharing.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RideSharingMockitoUnitTest {

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripService tripService;

    Trip trip_1 = new Trip();
    Trip trip_2 = new Trip();
    Driver driver = new Driver();

    @BeforeEach
    public void set_up() {
        trip_1.setDeparture("Antalya");
        trip_1.setArrival("Izmir");
        trip_1.setDate(LocalDate.of(2020, 6, 1));
        trip_1.setTime(LocalTime.of(12, 00, 00));
        trip_1.setPrice(100.0);
        trip_1.setSeat_number(1);
        trip_1.setDiscount(10.0);
        trip_1.setTotal_distance(500);
        trip_1.setTotal_duration(10);
        trip_1.setActive(true);
        trip_1.setBooked(false);
        driver.setId(1);
        trip_1.setDriver(driver);

        trip_2.setDeparture("Antalya");
        trip_2.setArrival("Istanbul");
        trip_2.setDate(LocalDate.of(2020, 6, 15));
        trip_2.setTime(LocalTime.of(15, 00, 00));
        trip_2.setPrice(100.0);
        trip_2.setSeat_number(2);
        trip_2.setDiscount(25.0);
        trip_2.setTotal_distance(750);
        trip_2.setTotal_duration(23);
        trip_2.setActive(true);
        trip_2.setBooked(false);
        trip_2.setDriver(driver);
    }

    @Test
    public void test_save_trip() throws Exception {
        when(tripRepository.save(any(Trip.class))).thenReturn(trip_1);
        Trip saved_trip = tripService.save(trip_1);
        assertThat(saved_trip).isNotNull();
        assertThat(saved_trip.getDeparture()).isSameAs(trip_1.getDeparture());
        assertThat(saved_trip.getArrival()).isSameAs(trip_1.getArrival());
    }

    @Test
    public void test_find_all_trips() throws Exception {
        List<Trip> actualList = new ArrayList<>();
        actualList.add(trip_1);
        actualList.add(trip_2);

        when(tripRepository.findAll()).thenReturn(actualList);
        List<Trip> expectedList = tripService.findAll();
        assertEquals(expectedList, actualList);

    }

    @Test
    public void test_find_trip_by_trip_id() throws Exception {
        when(tripRepository.findById(1)).thenReturn(Optional.of(trip_1));
        Trip expected_trip = tripService.findById(1);
        assertThat(expected_trip).isNotNull();
        assertEquals(expected_trip.getDeparture(), trip_1.getDeparture());
        assertThat(expected_trip.getDate()).isSameAs(trip_1.getDate());

    }

    @Test
    public void test_find_trip_by_driver_id() throws Exception {
        List<Trip> actualList = new ArrayList<>();
        actualList.add(trip_1);
        actualList.add(trip_2);

        when(tripRepository.findTripByDriverId(1)).thenReturn(actualList);
        List<Trip> expectedList = tripService.findTripByDriverId(1);
        assertThat(expectedList.get(0).getDeparture()).isSameAs(actualList.get(0).getDeparture());
        assertEquals(expectedList.get(1).getPrice(), actualList.get(1).getPrice());
        assertEquals(expectedList, actualList);
    }

    @Test
    public void test_find_trip_by_departure() throws Exception {
        List<Trip> actualList = new ArrayList<>();
        actualList.add(trip_1);
        actualList.add(trip_2);

        when(tripRepository.findTripByDeparture("Antalya")).thenReturn(actualList);
        List<Trip> expectedList = tripService.findTripByDeparture("Antalya");
        assertEquals(expectedList, actualList);
        assertThat(expectedList.get(0).getArrival()).isSameAs(actualList.get(0).getArrival());
        assertThat(expectedList.get(1).getArrival()).isSameAs(actualList.get(1).getArrival());
        assertFalse(expectedList.get(1).getArrival() == "Izmir");
    }

    @Test
    public void test_find_trip_by_arrival() throws Exception {
        List<Trip> actualList = new ArrayList<>();
        actualList.add(trip_1);

        when(tripRepository.findTripByArrival("Izmir")).thenReturn(actualList);
        List<Trip> expectedList = tripService.findTripByArrival("Izmir");
        assertEquals(expectedList, actualList);
        assertTrue(expectedList.size() == 1);
        assertEquals("Antalya", expectedList.get(0).getDeparture());
        assertEquals(LocalDate.of(2020,6,1), expectedList.get(0).getDate());
    }

    @Test
    public void test_find_trip_by_departure_and_arrival() throws Exception {
        List<Trip> actualList = new ArrayList<>();
        actualList.add(trip_2);

        when(tripRepository.findTripByDepartureAndArrival("Antalya", "Istanbul")).thenReturn(actualList);
        List<Trip> expectedList = tripService.findTripDepartureAndArrival("Antalya", "Istanbul");
        assertEquals(expectedList, actualList);
        assertTrue(expectedList.size() == 1);
        assertEquals(750, expectedList.get(0).getTotal_distance());
        assertEquals(23, expectedList.get(0).getTotal_duration());
    }

    @Test
    public void test_find_trip_by_departure_and_arrival_and_date() throws Exception {
        List<Trip> actualList = new ArrayList<>();
        actualList.add(trip_1);
        LocalDate date = LocalDate.of(2020, 6, 1);

        when(tripRepository.findTripByDepartureAndArrivalAndDate("Antalya", "Izmir", date)).thenReturn(actualList);
        List<Trip> expectedList = tripService.findTripDepartureAndArrivalAndDate("Antalya", "Izmir", date);
        assertEquals(expectedList, actualList);
        assertTrue(expectedList.size() == 1);
        assertEquals(LocalTime.of(12,00,00), expectedList.get(0).getTime());
        assertEquals(100.0, expectedList.get(0).getPrice());
        assertEquals(1, expectedList.get(0).getSeat_number());
    }

    @Test
    public void test_update_trip() throws Exception {
        when(tripRepository.findById(1)).thenReturn(Optional.of(trip_1));
        Trip trip = tripService.findById(1);
        trip.setPrice(150.0);
        trip.setDiscount(15.0);
        trip.setBooked(true);
        tripService.save(trip);
        assertEquals(150.0, trip.getPrice());
        assertEquals(15.0, trip.getDiscount());
        assertEquals(true, trip.isBooked());

    }

}
