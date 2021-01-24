package com.ivansop.weather.repository.custom;

import com.ivansop.weather.model.City;
import com.ivansop.weather.model.WeatherData;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

public interface WeatherDataRepositoryCustom {

    List<WeatherData> getTemperaturesForCitiesInInterval(Collection<Long> cityIds, ZonedDateTime from, ZonedDateTime to);

}
