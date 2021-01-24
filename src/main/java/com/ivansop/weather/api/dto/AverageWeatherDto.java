package com.ivansop.weather.api.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AverageWeatherDto {

    private long cityId;
    private String cityName;
    private Double averageTemperature;
    private String unit = "celsius"; // TODO could be configurable, hardcoded for the sake of API being clear

    public AverageWeatherDto() {
    }

    public AverageWeatherDto(long cityId, String cityName, Double averageTemperature) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.averageTemperature = averageTemperature;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(Double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("cityId", cityId)
                .append("cityName", cityName)
                .append("averageTemperature", averageTemperature)
                .append("unit", unit)
                .toString();
    }
}
