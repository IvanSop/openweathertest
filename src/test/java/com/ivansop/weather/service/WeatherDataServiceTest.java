package com.ivansop.weather.service;

import com.ivansop.weather.AbstractTest;
import com.ivansop.weather.api.dto.AverageWeatherDto;
import com.ivansop.weather.model.City;
import com.ivansop.weather.model.WeatherData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherDataServiceTest extends AbstractTest {

    @BeforeEach
    public void setUp() {
        cityRepository.deleteAll();
        weatherDataRepository.deleteAll();
    }

    /**
     * Test for {@link WeatherDataService#getAverageTemperature(List, ZonedDateTime, ZonedDateTime, Sort)}
     */
    @Test
    public void getAverageTest() {
        final City city = new City("ns");
        cityRepository.save(city);

        final Instant moment = Instant.ofEpochMilli(1611509553000L);
        final ZonedDateTime time = ZonedDateTime.ofInstant(moment, ZoneId.of("UTC"));

        final List<WeatherData> weatherDataList = new ArrayList<>();
        weatherDataList.add(new WeatherData(city, time, 1.0));
        weatherDataList.add(new WeatherData(city, time, 2.0));
        weatherDataList.add(new WeatherData(city, time, 30.0));
        weatherDataList.add(new WeatherData(city, time.plusHours(2), 100.0)); // falls out of calculation
        weatherDataMock.create(city, weatherDataList);

        final List<AverageWeatherDto> averageTemperatures = weatherDataService.getAverageTemperature(Collections.singletonList(city), time.minusMinutes(1), time.plusMinutes(1), null);

        assertEquals(1, averageTemperatures.size());
        assertEquals(11.0, averageTemperatures.get(0).getAverageTemperature());
    }

}
