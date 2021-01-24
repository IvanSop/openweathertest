package com.ivansop.weather.rest;

import com.ivansop.weather.rest.dto.OpenWeatherApiResult;

public interface OpenWeatherRest {

    OpenWeatherApiResult get5DayForecast(String cityName);

}
