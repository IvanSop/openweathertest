package com.ivansop.weather.repository.implementation;

import com.ivansop.weather.model.QWeatherData;
import com.ivansop.weather.model.WeatherData;
import com.ivansop.weather.repository.custom.WeatherDataRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

public class WeatherDataRepositoryImpl extends QuerydslRepositorySupport implements WeatherDataRepositoryCustom {

    public WeatherDataRepositoryImpl() {
        super(WeatherData.class);
    }

    @Override
    public List<WeatherData> getTemperaturesForCitiesInInterval(Collection<Long> cityIds, ZonedDateTime from, ZonedDateTime to) {
        final QWeatherData weatherData = QWeatherData.weatherData;

        return getQuerydsl().createQuery().select(weatherData)
                .distinct()
                .from(weatherData)
                .where(weatherData.time.after(from))
                .where(weatherData.time.before(to))
                .where(weatherData.city.id.in(cityIds))
                .fetch();
    }
}
