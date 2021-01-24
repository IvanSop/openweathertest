package com.ivansop.weather.repository;

import com.ivansop.weather.model.City;
import com.ivansop.weather.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    List<WeatherData> findByCityId(long cityId);

}
