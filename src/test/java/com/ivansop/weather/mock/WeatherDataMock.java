package com.ivansop.weather.mock;

import com.ivansop.weather.model.City;
import com.ivansop.weather.model.WeatherData;
import com.ivansop.weather.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class WeatherDataMock {

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    public void create(City city, WeatherData weatherData) {
        city.getWeatherDataList().add(weatherData);
        weatherData.setCity(city);
        weatherDataRepository.save(weatherData);
    }

    public void create(City city, List<WeatherData> weatherDataList) {
        weatherDataList.forEach(weatherData -> weatherData.setCity(city));
        city.getWeatherDataList().addAll(weatherDataList);
        weatherDataRepository.saveAll(weatherDataList);
    }

}
