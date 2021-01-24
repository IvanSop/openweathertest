package com.ivansop.weather.rest;

import com.ivansop.weather.configuration.WeatherProperties;
import com.ivansop.weather.rest.dto.OpenWeatherApiResult;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

/**
 * Service managing API calls to OpenWeather API.
 */
@Service
public class OpenWeatherRest {

    private static final String DEFAULT_UNITS = "metric";

    private final WeatherApi weatherApi;
    private final WeatherProperties weatherProperties;

    public OpenWeatherRest(WeatherProperties weatherProperties) {
        this.weatherProperties = weatherProperties;

        final Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient.Builder().build())
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        weatherApi = retrofit.create(WeatherApi.class);
    }

    /**
     * Fetches 5-day weather forecast from OpenWeather API for the given city.
     * @throws IllegalArgumentException If request fails for any reason.
     */
    public OpenWeatherApiResult get5DayForecast(String cityName) {
        final Call<OpenWeatherApiResult> call = weatherApi.get5DayForecast(cityName, weatherProperties.getOpenWeatherApiKey(), DEFAULT_UNITS);
        try {
            final Response<OpenWeatherApiResult> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }

            // TODO This could be improved, throw different errors depending on OpenWeather response.
            throw new IllegalArgumentException("FAILED_TO_FETCH_WEATHER_FORECAST");

        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("FAILED_TO_FETCH_WEATHER_FORECAST");
        }
    }
}
