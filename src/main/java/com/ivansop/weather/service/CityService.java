package com.ivansop.weather.service;

import com.ivansop.weather.model.City;
import com.ivansop.weather.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public City create(String cityName) {
        final City city = new City(cityName);
        return cityRepository.save(city);
    }

    public List<City> createBulk(Set<String> cityNames) {
        final List<City> cities = cityNames.stream()
                .map(City::new)
                .collect(Collectors.toList());

        return cityRepository.saveAll(cities);
    }
}
