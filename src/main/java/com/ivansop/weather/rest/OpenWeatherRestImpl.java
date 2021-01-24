package com.ivansop.weather.rest;

import com.ivansop.weather.configuration.WeatherProperties;
import com.ivansop.weather.rest.dto.OpenWeatherApiResult;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

/**
 * Service managing API calls to OpenWeather API.
 */
@Profile("production")
@Service
public class OpenWeatherRestImpl implements OpenWeatherRest {

    private static final String DEFAULT_UNITS = "metric";
    private static final String OPEN_WEATHER_API_URL = "https://api.openweathermap.org";

    private final WeatherApi weatherApi;
    private final WeatherProperties weatherProperties;

    public OpenWeatherRestImpl(WeatherProperties weatherProperties) {
        this.weatherProperties = weatherProperties;

        final Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient.Builder().build())
                .baseUrl(OPEN_WEATHER_API_URL)
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

            final ResponseBody errorBody = response.errorBody();
            final String errorMessage = errorBody == null ? "Unknown error" : errorBody.string();
            throw new IllegalArgumentException(errorMessage);

        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
