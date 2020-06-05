package com.project.ridesharing.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.project.ridesharing.model.Driver;
import com.project.ridesharing.model.Trip;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"/RideSharingDbUnitTest-context.xml"})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class RideSharingDbUnitTest {

    @Autowired
    private TripRepository tripRepository;

    @Test
    @DatabaseSetup("/trip_data.xml")
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/expected_save_data.xml")
    public void test_save_trip() throws Exception {
        Trip trip = new Trip();
        trip.setDeparture("Antalya");
        trip.setArrival("Izmir");
        trip.setDate(LocalDate.of(2020, 9, 9));
        trip.setTime(LocalTime.of(15, 00, 00));
        trip.setPrice(80.0);
        trip.setSeat_number(1);
        trip.setDiscount(12.0);
        trip.setTotal_distance(500);
        trip.setTotal_duration(10);
        trip.setActive(true);
        trip.setBooked(false);
        Driver driver = new Driver();
        driver.setId(1);
        trip.setDriver(driver);
        tripRepository.save(trip);
    }

    @Test
    @DatabaseSetup("/trip_data.xml")
    public void test_find_all_trips() throws Exception {
        List<Trip> tripList = tripRepository.findAll();
        assertEquals(2, tripList.size());
        assertEquals("Ankara", tripList.get(0).getDeparture());
    }

    @Test
    @DatabaseSetup("/trip_data.xml")
    public void test_find_trip_by_trip_id() throws Exception {
        LocalDate expected_date = LocalDate.of(2020, 07, 07);
        LocalTime expected_time = LocalTime.of(11, 00, 00);
        Trip trip = tripRepository.findById(2).orElse(null);
        assertEquals("Antalya", trip.getDeparture());
        assertEquals("Eskisehir", trip.getArrival());
        assertEquals(expected_date, trip.getDate());
        assertEquals(expected_time, trip.getTime());
    }

    @Test
    @DatabaseSetup("/trip_data.xml")
    public void test_find_trip_by_driver_id() throws Exception {
        List<Trip> tripList = tripRepository.findTripByDriverId(1);
        assertEquals(2, tripList.size());
        assertEquals("Trabzon", tripList.get(0).getArrival());
        assertEquals(110.0, tripList.get(0).getPrice());
        assertEquals(450, tripList.get(1).getTotal_distance());
    }

    @Test
    @DatabaseSetup("/trip_data.xml")
    public void test_find_trip_by_departure() throws Exception {
        List<Trip> tripList = tripRepository.findTripByDeparture("Ankara");
        assertEquals(1, tripList.size());
        assertEquals("Trabzon", tripList.get(0).getArrival());
        assertEquals(2, tripList.get(0).getSeat_number());
        assertEquals(6, tripList.get(0).getTotal_duration());
    }

    @Test
    @DatabaseSetup("/trip_data.xml")
    public void test_find_trip_by_arrival() throws Exception {
        List<Trip> tripList = tripRepository.findTripByArrival("Eskisehir");
        assertEquals(1, tripList.size());
        assertEquals("Antalya", tripList.get(0).getDeparture());
        assertEquals(true, tripList.get(0).isActive());
        assertEquals(false, tripList.get(0).isBooked());
    }

    @Test
    @DatabaseSetup("/trip_data.xml")
    public void test_find_trip_by_departure_and_arrival() throws Exception {
        List<Trip> tripList = tripRepository.findTripByDepartureAndArrival("Ankara", "Trabzon");
        assertEquals(1, tripList.size());
        assertEquals(110.0, tripList.get(0).getPrice());
        assertEquals(20.0, tripList.get(0).getDiscount());
        assertEquals(250, tripList.get(0).getTotal_distance());
    }

    @Test
    @DatabaseSetup("/trip_data.xml")
    public void test_find_trip_by_departure_and_arrival_and_date() throws Exception {
        LocalDate trip_date = LocalDate.of(2020, 07, 07);
        List<Trip> tripList = tripRepository.findTripByDepartureAndArrivalAndDate("Antalya", "Eskisehir", trip_date);
        assertEquals(1, tripList.size());
        assertEquals(4, tripList.get(0).getSeat_number());
        assertEquals(450, tripList.get(0).getTotal_distance());
        assertEquals(8, tripList.get(0).getTotal_duration());
    }

    @Test
    @DatabaseSetup("/trip_data.xml")
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/expected_update_data.xml")
    public void test_update_trip() throws Exception {
        Trip trip = tripRepository.findById(1).orElse(null);
        trip.setPrice(150.0);
        trip.setDiscount(25.0);
        trip.setBooked(true);
        tripRepository.save(trip);
    }

}
