package com.ivansop.weather.api;

import com.ivansop.weather.api.dto.AverageWeatherDto;
import com.ivansop.weather.model.City;
import com.ivansop.weather.service.CityService;
import com.ivansop.weather.service.TimeService;
import com.ivansop.weather.service.WeatherDataService;
import com.ivansop.weather.util.DateUtil;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@RequestMapping("/api/weather")
@RestController
public class WeatherApi {

    private final WeatherDataService weatherDataService;
    private final CityService cityService;
    private final TimeService timeService;

    public WeatherApi(WeatherDataService weatherDataService, CityService cityService, TimeService timeService) {
        this.weatherDataService = weatherDataService;
        this.cityService = cityService;
        this.timeService = timeService;
    }

    /**
     * Returns average temperature for given city Ids in a given time interval.
     * If cityIds is not provided, average temperature for all available cities is returned.
     * If a city with a provided id does not exists, it will be skipped and only data for the existing cities will be returned.
     *
     * @param from start of interval in seconds unix epoch time.
     * @param to   end of interval in seconds unix epoch time.
     */
    @GetMapping("/temperature")
    public ResponseEntity<List<AverageWeatherDto>> getAverageTemperature(@RequestParam(required = false) Set<Long> cityIds,
                                                                         @RequestParam Long from,
                                                                         @RequestParam Long to,
                                                                         Sort sort) {

        final ZonedDateTime fromZDT = DateUtil.toZDT(from);
        final ZonedDateTime toZDT = DateUtil.toZDT(to);
        validateDateRange(fromZDT, toZDT);

        final List<City> cities = CollectionUtils.isEmpty(cityIds)
                ? cityService.list()
                : cityService.listByIdIn(cityIds);

        final List<AverageWeatherDto> averageTemperatures = weatherDataService.getAverageTemperature(cities, fromZDT, toZDT, sort);

        return new ResponseEntity<>(averageTemperatures, HttpStatus.OK);
    }

    /**
     * Returns average temperature for given city names in a given time interval.
     * If city names are not provided, average temperature for all available cities is returned.
     * If a city with a provided name does not exists, it will be skipped and only data for the existing cities will be returned.
     *
     * @param from start of interval in seconds unix epoch time.
     * @param to   end of interval in seconds unix epoch time.
     */
    @GetMapping(value = "/temperature", produces = "application/vnd.named+json")
    public ResponseEntity<List<AverageWeatherDto>> getAverageTemperatureByCityName(@RequestParam(required = false) Set<String> cityNames,
                                                                                   @RequestParam Long from,
                                                                                   @RequestParam Long to, Sort sort) {


        final ZonedDateTime fromZDT = DateUtil.toZDT(from);
        final ZonedDateTime toZDT = DateUtil.toZDT(to);
        validateDateRange(fromZDT, toZDT);

        final List<City> cities = CollectionUtils.isEmpty(cityNames)
                ? cityService.list()
                : cityService.listByNameIn(cityNames);

        final List<AverageWeatherDto> averageTemperatures = weatherDataService.getAverageTemperature(cities, fromZDT, toZDT, sort);

        return new ResponseEntity<>(averageTemperatures, HttpStatus.OK);
    }

    /**
     * Provided timestamps must not be before current time and more than 5 days ahead in time.
     */
    private void validateDateRange(ZonedDateTime from, ZonedDateTime to) {
        final ZonedDateTime now = timeService.getCurrentTime();

        if (from.isBefore(now)) {
            throw new IllegalArgumentException("START_TIME_MUST_BE_AFTER_CURRENT_TIME");
        }

        if (to.isAfter(now.plusDays(5))) {
            throw new IllegalArgumentException("END_TIME_MUST_NOT_EXCEED_5_DAYS_FROM_NOW");
        }

        if (!to.isAfter(from)) {
            throw new IllegalArgumentException("END_TIME_MUST_BE_AFTER_START_TIME");
        }
    }
}
