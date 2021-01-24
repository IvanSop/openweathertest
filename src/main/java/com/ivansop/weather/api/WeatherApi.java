package com.ivansop.weather.api;

import com.ivansop.weather.configuration.WeatherProperties;
import com.ivansop.weather.dto.WeatherDto;
import com.ivansop.weather.service.CityService;
import com.ivansop.weather.service.WeatherDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RequestMapping("/api/weather")
@RestController
public class WeatherApi {

    @Autowired
    private WeatherProperties weatherProperties;

    @Autowired
    private WeatherDataService weatherDataService;

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<WeatherDto> test() {
        final Set<String> cities = weatherProperties.getCities();
        final WeatherDto weatherDto = new WeatherDto();
        return new ResponseEntity<>(weatherDto, HttpStatus.OK);
    }

}
