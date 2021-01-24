package com.ivansop.weather.service;

import com.ivansop.weather.api.dto.AverageWeatherDto;
import com.ivansop.weather.model.City;
import com.ivansop.weather.model.WeatherData;
import com.ivansop.weather.repository.CityRepository;
import com.ivansop.weather.repository.WeatherDataRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WeatherDataService {

    private final WeatherDataRepository weatherDataRepository;

    public WeatherDataService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    public WeatherData save(WeatherData weatherData) {
        return weatherDataRepository.save(weatherData);
    }

    public List<WeatherData> saveAll(List<WeatherData> weatherDataList) {
        return weatherDataRepository.saveAll(weatherDataList);
    }

    public List<AverageWeatherDto> getAverageTemperature(List<City> cities, ZonedDateTime from, ZonedDateTime to, Sort sort) {
        final List<Long> cityIds = cities.stream()
                .map(City::getId)
                .collect(Collectors.toList());

        final Map<City, List<WeatherData>> temperaturesByCity = weatherDataRepository.getTemperaturesForCitiesInInterval(cityIds, from, to)
                .stream()
                .collect(Collectors.groupingBy(WeatherData::getCity));

        cities.forEach(city -> {
            city.setWeatherDataList(temperaturesByCity.getOrDefault(city, new ArrayList<>()));
        });

        return cities.stream()
                .map(city -> {
                    final Double temperature = calculateAverage(city.getWeatherDataList());
                    return new AverageWeatherDto(city.getId(), city.getName(), temperature);
                })
                .sorted(getComparator(sort))
                .collect(Collectors.toList());
    }

    /**
     * Handles sorting by average temperature.
     */
    private Comparator<AverageWeatherDto> getComparator(Sort sort) {
        final Sort.Order averageTemperatureOrder = sort == null ? null : sort.getOrderFor("averageTemperature");

        if (averageTemperatureOrder == null) {
            return Comparator.comparing(AverageWeatherDto::getCityName);
        } else if (averageTemperatureOrder.isAscending()) {
            return Comparator.nullsLast(Comparator.comparingDouble(AverageWeatherDto::getAverageTemperature));
        } else {
            return Comparator.nullsLast(Comparator.comparingDouble(AverageWeatherDto::getAverageTemperature)).reversed();
        }
    }

    private Double calculateAverage(List<WeatherData> weatherDataList) {
        if (weatherDataList.isEmpty()) {
            return null;
        }

        return weatherDataList.stream()
                .map(WeatherData::getTemperature)
                .collect(Collectors.averagingDouble(Double::doubleValue));
    }
}
