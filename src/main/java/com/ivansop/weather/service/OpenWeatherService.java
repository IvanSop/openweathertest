package com.ivansop.weather.service;

import com.ivansop.weather.configuration.WeatherProperties;
import com.ivansop.weather.model.City;
import com.ivansop.weather.model.WeatherData;
import com.ivansop.weather.rest.OpenWeatherRest;
import com.ivansop.weather.rest.dto.OpenWeatherApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OpenWeatherService {

    @Autowired
    private OpenWeatherRest openWeatherRest;

    @Autowired
    private WeatherProperties weatherProperties;

    @Autowired
    private CityService cityService;

    @Autowired
    private WeatherDataService weatherDataService;

    @PostConstruct
    public void initializeData() {
        final Set<String> cityNames = weatherProperties.getCities();
        final List<City> createdCities = cityService.createBulk(cityNames);

        final List<WeatherData> allWeatherData = createdCities.stream()
                .map(city -> {
                    final OpenWeatherApiResult result = openWeatherRest.get5DayForecast(city.getName());
                    final List<WeatherData> weatherDataList = result.getList().stream()
                            .map(openWeatherItemDto -> {
                                final double temperature = openWeatherItemDto.getDetails().getTemperature();
                                final long timestamp = openWeatherItemDto.getTimestamp();
                                final WeatherData weatherData = new WeatherData();
                                weatherData.setCity(city);
                                weatherData.setTemperature(temperature);
                                weatherData.setTime(ZonedDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.of("UTC")));
                                return weatherData;
                            })
                            .collect(Collectors.toList());

                    return weatherDataList;
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        weatherDataService.saveAll(allWeatherData);
    }
}
