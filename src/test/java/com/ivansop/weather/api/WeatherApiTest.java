package com.ivansop.weather.api;

import com.ivansop.weather.AbstractTest;
import com.ivansop.weather.model.City;
import com.ivansop.weather.model.WeatherData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WeatherApiTest extends AbstractTest {

    @BeforeEach
    public void setUp() {
        cityRepository.deleteAll();
        weatherDataRepository.deleteAll();
        timeService.setCurrentTime(null);
    }

    /**
     * Test for {@link WeatherApi#getAverageTemperature(Set, Long, Long, Sort)}
     * Test for {@link WeatherApi#getAverageTemperatureByCityName(Set, Long, Long, Sort)}
     */
    @Test
    public void getAverageFor5DaysTest() throws Exception {
        final City city1 = new City("ns");
        final City city2 = new City("bg");
        final City city3 = new City("so");
        final City city4 = new City("ff");
        cityRepository.save(city1);
        cityRepository.save(city2);
        cityRepository.save(city3);
        cityRepository.save(city4);

        final ZonedDateTime time = ZonedDateTime.now();
        timeService.setCurrentTime(time);

        // avg 18.0 for city 1
        final WeatherData city1Temperature1 = new WeatherData(city1, time.plusSeconds(10), 12.0);
        weatherDataMock.create(city1, city1Temperature1);

        final WeatherData city1Temperature2 = new WeatherData(city1, time.plusSeconds(50), 24.0);
        weatherDataMock.create(city1, city1Temperature2);

        // avg 5.0 for city 2
        final WeatherData city2Temperature1 = new WeatherData(city2, time.plusSeconds(10), 5.0);
        weatherDataMock.create(city2, city2Temperature1);

        final WeatherData city2Temperature2 = new WeatherData(city2, time.plusSeconds(50), 5.0);
        weatherDataMock.create(city2, city2Temperature2);

        // avg 150.0 for city 3
        final WeatherData city3Temperature1 = new WeatherData(city3, time.plusSeconds(10), 100.0);
        weatherDataMock.create(city3, city3Temperature1);

        final WeatherData city3Temperature2 = new WeatherData(city3, time.plusSeconds(50), 200.0);
        weatherDataMock.create(city3, city3Temperature2);

        // irrelevant
        final WeatherData city4Temperature1 = new WeatherData(city4, time.plusSeconds(10), 300.0);
        weatherDataMock.create(city4, city4Temperature1);
        final WeatherData city4Temperature2 = new WeatherData(city4, time.plusSeconds(50), 200.0);
        weatherDataMock.create(city4, city4Temperature2);

        final MockHttpServletRequestBuilder requestById = get("/api/weather/temperature")
                .param("cityIds", city1.getId() + "," + city2.getId() + "," + city3.getId())
                .param("sort", "averageTemperature,asc")
                .param("from", time.plusSeconds(2).toEpochSecond() + "")
                .param("to", time.plusSeconds(60).toEpochSecond() + "");

        mockMvc.perform(requestById)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].cityName").value(city2.getName()))
                .andExpect(jsonPath("$[0].averageTemperature").value("5.0"))
                .andExpect(jsonPath("$[1].cityName").value(city1.getName()))
                .andExpect(jsonPath("$[1].averageTemperature").value("18.0"))
                .andExpect(jsonPath("$[2].cityName").value(city3.getName()))
                .andExpect(jsonPath("$[2].averageTemperature").value("150.0"));

        final MockHttpServletRequestBuilder requestByName = get("/api/weather/temperature")
                .accept("application/vnd.named+json")
                .param("cityNames", "ns,bg,so")
                .param("sort", "averageTemperature,asc")
                .param("from", time.plusSeconds(2).toEpochSecond() + "")
                .param("to", time.plusSeconds(60).toEpochSecond() + "");

        mockMvc.perform(requestByName)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].cityName").value(city2.getName()))
                .andExpect(jsonPath("$[0].averageTemperature").value("5.0"))
                .andExpect(jsonPath("$[1].cityName").value(city1.getName()))
                .andExpect(jsonPath("$[1].averageTemperature").value("18.0"))
                .andExpect(jsonPath("$[2].cityName").value(city3.getName()))
                .andExpect(jsonPath("$[2].averageTemperature").value("150.0"));

        final MockHttpServletRequestBuilder requestByIdSortDesc = get("/api/weather/temperature")
                .param("cityIds", city1.getId() + "," + city2.getId() + "," + city3.getId())
                .param("sort", "averageTemperature,desc")
                .param("from", time.plusSeconds(2).toEpochSecond() + "")
                .param("to", time.plusSeconds(60).toEpochSecond() + "");

        mockMvc.perform(requestByIdSortDesc)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].cityName").value(city2.getName()))
                .andExpect(jsonPath("$[2].averageTemperature").value("5.0"))
                .andExpect(jsonPath("$[1].cityName").value(city1.getName()))
                .andExpect(jsonPath("$[1].averageTemperature").value("18.0"))
                .andExpect(jsonPath("$[0].cityName").value(city3.getName()))
                .andExpect(jsonPath("$[0].averageTemperature").value("150.0"));
    }

    /**
     * Test for {@link WeatherApi#getAverageTemperature(Set, Long, Long, Sort)}
     * Test for {@link WeatherApi#getAverageTemperatureByCityName(Set, Long, Long, Sort)}
     */
    @Test
    public void getAverageFor5DaysInvalidTimeTest() throws Exception {
        final ZonedDateTime time = ZonedDateTime.now();
        timeService.setCurrentTime(time);

        final MockHttpServletRequestBuilder request = get("/api/weather/temperature")
                .param("cityIds", "1,2")
                .param("sort", "averageTemperature,asc")
                .param("from", time.minusSeconds(2).toEpochSecond() + "")
                .param("to", time.plusSeconds(60).toEpochSecond() + "");

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    /**
     * Test for {@link WeatherApi#getAverageTemperature(Set, Long, Long, Sort)}
     */
    @Test
    public void getAverageFor5DaysNoCityParameter() throws Exception {
        final ZonedDateTime time = ZonedDateTime.now();
        timeService.setCurrentTime(time);

        final City city1 = new City("city1");
        final City city2 = new City("city2");
        cityRepository.save(city1);
        cityRepository.save(city2);

        weatherDataMock.create(city1, new WeatherData(city1, time.plusSeconds(10), 12.0));

        final MockHttpServletRequestBuilder request = get("/api/weather/temperature")
                .param("from", time.plusSeconds(2).toEpochSecond() + "")
                .param("to", time.plusSeconds(60).toEpochSecond() + "");

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].cityName").value(city1.getName()))
                .andExpect(jsonPath("$[0].averageTemperature").value("12.0"))
                .andExpect(jsonPath("$[1].cityName").value(city2.getName()))
                .andExpect(jsonPath("$[1].averageTemperature", nullValue()));
    }

    /**
     * Test for {@link WeatherApi#getAverageTemperature(Set, Long, Long, Sort)}
     */
    @Test
    public void getAverageFor5DaysNamedNoCityParameter() throws Exception {
        final ZonedDateTime time = ZonedDateTime.now();
        timeService.setCurrentTime(time);

        final City city1 = new City("city1");
        final City city2 = new City("city2");
        cityRepository.save(city1);
        cityRepository.save(city2);

        weatherDataMock.create(city1, new WeatherData(city1, time.plusSeconds(10), 12.0));
        weatherDataMock.create(city2, new WeatherData(city2, time.plusSeconds(10), 10.0));

        final MockHttpServletRequestBuilder requestNamed = get("/api/weather/temperature")
                .accept("application/vnd.named+json")
                .param("sort", "averageTemperature")
                .param("from", time.plusSeconds(2).toEpochSecond() + "")
                .param("to", time.plusSeconds(60).toEpochSecond() + "");

        mockMvc.perform(requestNamed)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].cityName").value(city2.getName()))
                .andExpect(jsonPath("$[0].averageTemperature").value("10.0"))
                .andExpect(jsonPath("$[1].cityName").value(city1.getName()))
                .andExpect(jsonPath("$[1].averageTemperature").value("12.0"));
    }
}
