package com.ivansop.weather.service;

import com.ivansop.weather.model.WeatherData;
import com.ivansop.weather.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherDataService {

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    public WeatherData save(WeatherData weatherData) {
        return weatherDataRepository.save(weatherData);
    }

    public List<WeatherData> saveAll(List<WeatherData> weatherDataList) {
        return weatherDataRepository.saveAll(weatherDataList);
    }
}
