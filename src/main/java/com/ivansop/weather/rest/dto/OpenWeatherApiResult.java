package com.ivansop.weather.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Contains the data received from OpenWeather API 5 day forecast.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherApiResult {

    @JsonProperty("cnt")
    private int count;

    private List<OpenWeatherItemDto> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<OpenWeatherItemDto> getList() {
        return list;
    }

    public void setList(List<OpenWeatherItemDto> list) {
        this.list = list;
    }
}
