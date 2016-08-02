package com.example.michaeljeffress.project3;

/**
 * Created by audreyeso on 8/1/16.
 */


import com.example.michaeljeffress.project3.models.ModelRoot;
import com.example.michaeljeffress.project3.modelsWeeklyWeather.ModelRootWeeklyWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherInterface {

    @GET("data/2.5/weather")
    Call<ModelRoot> getCurrentWeather(@Query("lat") double latitude,
                                      @Query("lon") double longitude,
                                      @Query("APPID") String appid);

    @GET("data/2.5/forecast")
    Call<ModelRootWeeklyWeather> getWeeklyWeather(@Query("lat") double latitude,
                                                  @Query("lon") double longitude,
                                                  @Query("APPID") String appid);
}
