package com.project.ridesharing.controller;

import com.project.ridesharing.model.City;
import com.project.ridesharing.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CityController {

    @Autowired
    CityService cityService;

    // save a city
    @PostMapping("/city/save")
    public City save(@Validated @RequestBody City city) {
        return cityService.save(city);
    }

    // get all cities
    @GetMapping("/city/all")
    public List<City> findAll() {
        return cityService.findAll();
    }

    // get a city by city_id
    @GetMapping("/city/{id}")
    public ResponseEntity<City> getCityById(@PathVariable(value = "id") Integer city_id) {
        City city = cityService.findById(city_id);
        if (city == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(city);
    }

    // update a city by city_id
    @PutMapping("/city/update/{id}")
    public ResponseEntity<City> updateCityById(@PathVariable(value = "id") Integer city_id, @Validated @RequestBody City new_city) {
        City city = cityService.findById(city_id);
        if (city == null) {
            return ResponseEntity.notFound().build();
        }
        city.setCity_name(new_city.getCity_name());
        City updated_city = cityService.save(city);
        return ResponseEntity.ok().body(updated_city);
    }

    // delete a city by city_id
    @DeleteMapping("/city/delete/{id}")
    public ResponseEntity<City> deleteCityById(@PathVariable(value = "id") Integer city_id) {
        City city = cityService.findById(city_id);
        if (city == null) {
            return ResponseEntity.notFound().build();
        }
        cityService.delete(city);
        return ResponseEntity.ok().build();
    }

}
