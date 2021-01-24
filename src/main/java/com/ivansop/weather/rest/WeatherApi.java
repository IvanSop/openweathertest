package com.ivansop.weather.rest;

import com.ivansop.weather.rest.dto.OpenWeatherApiResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("/data/2.5/forecast")
    Call<OpenWeatherApiResult> get5DayForecast(@Query("q") String city,
                                               @Query("appId") String apiKey,
                                               @Query("units") String units);

}
