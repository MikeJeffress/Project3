package com.example.michaeljeffress.project3.modelsWeeklyWeather;

/**
 * Created by audreyeso on 8/2/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CloudsWeeklyWeather {

    @SerializedName("all")
    @Expose
    private Integer all;

    /**
     *
     * @return
     * The all
     */
    public Integer getAll() {
        return all;
    }

    /**
     *
     * @param all
     * The all
     */
    public void setAll(Integer all) {
        this.all = all;
    }

}
