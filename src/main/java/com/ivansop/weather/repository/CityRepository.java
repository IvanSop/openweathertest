package com.ivansop.weather.repository;

import com.ivansop.weather.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findById(long id);

    Optional<City> findByName(String name);

}
