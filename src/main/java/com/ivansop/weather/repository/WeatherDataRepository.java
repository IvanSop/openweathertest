package com.ivansop.weather.repository;

import com.ivansop.weather.model.WeatherData;
import com.ivansop.weather.repository.custom.WeatherDataRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long>, WeatherDataRepositoryCustom {


}
