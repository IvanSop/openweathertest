package com.ivansop.weather.configuration;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@ConfigurationProperties(prefix = "weather", ignoreUnknownFields = false)
public class WeatherProperties {

    private String openWeatherApiKey;
    private Set<String> cities = new HashSet<>();

    public String getOpenWeatherApiKey() {
        return openWeatherApiKey;
    }

    public void setOpenWeatherApiKey(String openWeatherApiKey) {
        this.openWeatherApiKey = openWeatherApiKey;
    }

    public Set<String> getCities() {
        return cities;
    }

    public void setCities(Set<String> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("openWeatherApiKey", openWeatherApiKey)
                .append("cities", cities)
                .toString();
    }
}
