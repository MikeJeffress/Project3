package com.example.michaeljeffress.project3.modelsWeeklyWeather;

/**
 * Created by audreyeso on 8/2/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Sys_WeeklyWeather {

    @SerializedName("pod")
    @Expose
    private String pod;

    /**
     *
     * @return
     * The pod
     */
    public String getPod() {
        return pod;
    }

    /**
     *
     * @param pod
     * The pod
     */
    public void setPod(String pod) {
        this.pod = pod;
    }

}