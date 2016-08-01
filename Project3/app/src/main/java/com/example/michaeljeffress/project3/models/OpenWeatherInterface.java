package com.example.michaeljeffress.project3.models;

/**
 * Created by audreyeso on 8/1/16.
 */


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherInterface {

    @GET("/data/2.5/weather")
    Call<Model> getCurrentWeather(@Query("lat") long latitude,
                                  @Query("long") long longitude,
                                  @Query("appid") String appid);

    }

