package com.project.ridesharing.repository;

import com.project.ridesharing.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    List<Driver> findDriverByCityId(Integer city_id);
    List<Driver> findDriverByCarId(Integer car_id);
}
