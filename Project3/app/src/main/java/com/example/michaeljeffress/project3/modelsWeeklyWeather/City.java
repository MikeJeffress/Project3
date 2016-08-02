package com.example.michaeljeffress.project3.modelsWeeklyWeather;

/**
 * Created by audreyeso on 8/2/16.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class City {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("coord")
    @Expose
    private CoordWeeklyWeather coord;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("population")
    @Expose
    private Integer population;
    @SerializedName("sys")
    @Expose
    private SystemWeeklyWeather sys;

    /**
     *
     * @return
     * The id
     */
    public long getIdWeeklyWeather() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getNameWeeklyWeather() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The coord
     */
    public CoordWeeklyWeather getCoordWeeklyWeather() {
        return coord;
    }

    /**
     *
     * @param coord
     * The coord
     */
    public void setCoord(CoordWeeklyWeather coord) {
        this.coord = coord;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountryWeeklyWeather(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The population
     */
    public Integer getPopulationWeeklyWeather() {
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

    /**
     *
     * @return
     * The sys
     */
    public SystemWeeklyWeather getSystemWeeklyWeather() {
        return sys;
    }

    /**
     *
     * @param sys
     * The sys
     */
    public void setSysWeeklyWeather(SystemWeeklyWeather sys) {
        this.sys = sys;
    }

}
