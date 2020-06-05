package com.project.ridesharing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ridesharing.model.Driver;
import com.project.ridesharing.model.Trip;
import com.project.ridesharing.service.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = TripController.class)
@ActiveProfiles("test")
public class RideSharingMockMvcUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TripService tripService;

    @Autowired
    private ObjectMapper objectMapper;

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
        given(tripService.save(any(Trip.class))).willAnswer((invocation) -> invocation.getArgument(0));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/trip/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trip_1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.departure", is(trip_1.getDeparture())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.arrival", is(trip_1.getArrival())));
    }

    @Test
    public void test_find_all_trips() throws Exception {
        List<Trip> tripList = new ArrayList<>();
        tripList.add(trip_1);
        tripList.add(trip_2);
        given(tripService.findAll()).willReturn(tripList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/trip/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(tripList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].departure", is(tripList.get(0).getDeparture())));
    }

    @Test
    public void test_find_trip_by_trip_id() throws Exception {
        given(tripService.findById(1)).willReturn(trip_1);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/trip/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.departure", is(trip_1.getDeparture())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.arrival", is(trip_1.getArrival())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", is(trip_1.getPrice())));

    }

    @Test
    public void test_find_trip_by_driver_id() throws Exception {
        List<Trip> tripList = new ArrayList<>();
        tripList.add(trip_1);
        tripList.add(trip_2);
        given(tripService.findTripByDriverId(1)).willReturn(tripList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/trip/driver/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(tripList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].departure", is(tripList.get(0).getDeparture())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].arrival", is(tripList.get(1).getArrival())));
    }

    @Test
    public void test_find_trip_by_departure() throws Exception {
        List<Trip> tripList = new ArrayList<>();
        tripList.add(trip_1);
        tripList.add(trip_2);
        given(tripService.findTripByDeparture("Antalya")).willReturn(tripList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/trip/departure/{departure}", "Antalya"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(tripList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].total_duration", is(tripList.get(0).getTotal_duration())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].arrival", is(tripList.get(1).getArrival())));
    }

    @Test
    public void test_find_trip_by_arrival() throws Exception {
        List<Trip> tripList = new ArrayList<>();
        tripList.add(trip_2);
        given(tripService.findTripByArrival("Istanbul")).willReturn(tripList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/trip/arrival/{arrival}", "Istanbul"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(tripList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].departure", is(tripList.get(0).getDeparture())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].total_distance", is(tripList.get(0).getTotal_distance())));
    }

    @Test
    public void test_find_trip_by_departure_and_arrival() throws Exception {
        List<Trip> tripList = new ArrayList<>();
        tripList.add(trip_1);
        given(tripService.findTripDepartureAndArrival("Antalya", "Izmir")).willReturn(tripList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/trip/location/{departure}-{arrival}", "Antalya", "Izmir"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(tripList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].departure", is(tripList.get(0).getDeparture())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].arrival", is(tripList.get(0).getArrival())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].price", is(tripList.get(0).getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].discount", is(tripList.get(0).getDiscount())));
    }

    @Test
    public void test_find_trip_by_departure_and_arrival_and_date() throws Exception {
        List<Trip> tripList = new ArrayList<>();
        tripList.add(trip_2);
        given(tripService.findTripDepartureAndArrivalAndDate("Antalya", "Istanbul", LocalDate.of(2020, 6, 15)))
                .willReturn(tripList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/trip/date/{departure}-{arrival}?date={date}", "Antalya", "Istanbul", "2020-06-15"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(tripList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].departure", is(tripList.get(0).getDeparture())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].arrival", is(tripList.get(0).getArrival())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].booked", is(tripList.get(0).isBooked())));
    }

}
