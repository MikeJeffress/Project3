package com.example.michaeljeffress.project3.modelsWeeklyWeather;

/**
 * Created by audreyeso on 8/2/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class List {

    @SerializedName("dt")
    @Expose
    private long dt; // TODO make into long
    @SerializedName("main")
    @Expose
    private MainWeeklyWeather main;
    @SerializedName("weather")
    @Expose
    private java.util.List<WeatherWeeklyWeather> weather = new ArrayList<WeatherWeeklyWeather>();
    @SerializedName("clouds")
    @Expose
    private CloudsWeeklyWeather clouds;
    @SerializedName("wind")
    @Expose
    private WindWeeklyWind wind;
    @SerializedName("rain")
    @Expose
    private RainWeeklyWeather rain;
    @SerializedName("sys")
    @Expose
    private Sys_WeeklyWeather sys;
    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;

    /**
     *
     * @return
     * The dt
     */
    public long getDt() {
        return dt;
    }

    /**
     *
     * @param dt
     * The dt
     */
    public void setDt(long dt) {
        this.dt = dt;
    }

    /**
     *
     * @return
     * The main
     */
    public MainWeeklyWeather getMain() {
        return main;
    }

    /**
     *
     * @param main
     * The main
     */
    public void setMain(MainWeeklyWeather main) {
        this.main = main;
    }

    /**
     *
     * @return
     * The weather
     */
    public java.util.List<WeatherWeeklyWeather> getWeather() {
        return weather;
    }

    /**
     *
     * @param weather
     * The weather
     */
    public void setWeather(java.util.List<WeatherWeeklyWeather> weather) {
        this.weather = weather;
    }

    /**
     *
     * @return
     * The clouds
     */
    public CloudsWeeklyWeather getClouds() {
        return clouds;
    }

    /**
     *
     * @param clouds
     * The clouds
     */
    public void setClouds(CloudsWeeklyWeather clouds) {
        this.clouds = clouds;
    }

    /**
     *
     * @return
     * The wind
     */
    public WindWeeklyWind getWind() {
        return wind;
    }

    /**
     *
     * @param wind
     * The wind
     */
    public void setWind(WindWeeklyWind wind) {
        this.wind = wind;
    }

    /**
     *
     * @return
     * The rain
     */
    public RainWeeklyWeather getRain() {
        return rain;
    }

    /**
     *
     * @param rain
     * The rain
     */
    public void setRain(RainWeeklyWeather rain) {
        this.rain = rain;
    }

    /**
     *
     * @return
     * The sys
     */
    public Sys_WeeklyWeather getSys() {
        return sys;
    }

    /**
     *
     * @param sys
     * The sys
     */
    public void setSys(Sys_WeeklyWeather sys) {
        this.sys = sys;
    }

    /**
     *
     * @return
     * The dtTxt
     */
    public String getDtTxt() {
        return dtTxt;
    }

    /**
     *
     * @param dtTxt
     * The dt_txt
     */
    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

}