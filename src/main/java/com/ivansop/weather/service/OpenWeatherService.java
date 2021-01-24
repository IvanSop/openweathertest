package com.ivansop.weather.service;

import com.ivansop.weather.configuration.WeatherProperties;
import com.ivansop.weather.model.City;
import com.ivansop.weather.model.WeatherData;
import com.ivansop.weather.rest.OpenWeatherRest;
import com.ivansop.weather.rest.dto.OpenWeatherApiResult;
import com.ivansop.weather.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Processes and saves city data and fetched data from open weather api.
 */
@Service
public class OpenWeatherService {

    private final OpenWeatherRest openWeatherRest;
    private final WeatherProperties weatherProperties;
    private final CityService cityService;
    private final WeatherDataService weatherDataService;

    public OpenWeatherService(OpenWeatherRest openWeatherRest, WeatherProperties weatherProperties, CityService cityService, WeatherDataService weatherDataService) {
        this.openWeatherRest = openWeatherRest;
        this.weatherProperties = weatherProperties;
        this.cityService = cityService;
        this.weatherDataService = weatherDataService;
    }

    /**
     * Creates city entities and fetches temperature data.
     */
    @PostConstruct
    public void initializeData() {
        final Set<String> cityNames = weatherProperties.getCities();

        cityNames.forEach(cityName -> {
            try {
                final OpenWeatherApiResult result = openWeatherRest.get5DayForecast(cityName);
                final City city = cityService.create(cityName);
                final List<WeatherData> weatherDataForCity = createWeatherDataFromDto(result, city);
                weatherDataService.saveAll(weatherDataForCity);
            } catch (Exception e) {
                System.out.println("Failed to fetch data for city " + cityName);
                e.printStackTrace();
            }
        });
    }

    private List<WeatherData> createWeatherDataFromDto(OpenWeatherApiResult result, City city) {
        return result.getList().stream()
                .map(item -> {
                    final double temperature = item.getDetails().getTemperature();
                    final long timestamp = item.getTimestamp();
                    final WeatherData weatherData = new WeatherData();
                    weatherData.setCity(city);
                    weatherData.setTemperature(temperature);
                    weatherData.setTime(DateUtil.toZDT(timestamp));
                    return weatherData;
                })
                .collect(Collectors.toList());
    }
}
