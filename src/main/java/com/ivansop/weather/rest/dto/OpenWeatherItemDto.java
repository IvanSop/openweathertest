package com.ivansop.weather.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Part of the OpenWeather API 5 day forecast response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherItemDto {

    @JsonProperty("dt")
    private long timestamp;

    @JsonProperty("main")
    private OpenWeatherItemDetailsDto details;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public OpenWeatherItemDetailsDto getDetails() {
        return details;
    }

    public void setDetails(OpenWeatherItemDetailsDto details) {
        this.details = details;
    }
}
