package com.ivansop.weather.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Part of the OpenWeather API 5 day forecast response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherItemDetailsDto {

    @JsonProperty("temp")
    private double temperature;

    @JsonProperty("temp_min")
    private double minimumTemperature;

    @JsonProperty("temp_max")
    private double maximumTemperature;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getMinimumTemperature() {
        return minimumTemperature;
    }

    public void setMinimumTemperature(double minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
    }

    public double getMaximumTemperature() {
        return maximumTemperature;
    }

    public void setMaximumTemperature(double maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
    }
}
