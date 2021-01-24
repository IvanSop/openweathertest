package com.ivansop.weather.rest;

import com.ivansop.weather.rest.dto.OpenWeatherApiResult;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Profile("test")
@Service
public class OpenWeatherRestMock implements OpenWeatherRest {

    /**
     * Just returns empty list, doesn't call the open weather api.
     */
    @Override
    public OpenWeatherApiResult get5DayForecast(String cityName) {
        final OpenWeatherApiResult result = new OpenWeatherApiResult();
        result.setList(Collections.emptyList());

        return result;
    }
}
