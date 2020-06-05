package com.project.ridesharing.service;

import com.project.ridesharing.model.City;
import com.project.ridesharing.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    // save a city
    public City save(City city) {
        return cityRepository.save(city);
    }

    // get all cities
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    // get a city by city_id
    public City findById(Integer city_id) {
        return cityRepository.findById(city_id).orElse(null);
    }

    // delete a city by city object
    public void delete(City city) {
        cityRepository.delete(city);
    }

}
