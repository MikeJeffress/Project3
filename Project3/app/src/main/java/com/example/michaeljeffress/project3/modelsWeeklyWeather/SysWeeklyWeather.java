package com.example.michaeljeffress.project3.modelsWeeklyWeather;

/**
 * Created by audreyeso on 8/2/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SysWeeklyWeather {

    @SerializedName("population")
    @Expose
    private Integer population;

    /**
     *
     * @return
     * The population
     */
    public Integer getPopulation() {
        return population;
    }

    /**
     *
     * @param population
     * The population
     */
    public void setPopulation(Integer population) {
        this.population = population;
    }

}