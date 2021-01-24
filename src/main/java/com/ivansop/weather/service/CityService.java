package com.ivansop.weather.service;

import com.ivansop.weather.model.City;
import com.ivansop.weather.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City create(String cityName) {
        final City city = new City(cityName);
        return cityRepository.save(city);
    }

    public List<City> list() {
        return cityRepository.findAll();
    }

    public List<City> listByIdIn(Collection<Long> ids) {
        return cityRepository.findByIdIn(ids);
    }

    public List<City> listByNameIn(Collection<String> names) {
        return cityRepository.findByNameIn(names);
    }
}
