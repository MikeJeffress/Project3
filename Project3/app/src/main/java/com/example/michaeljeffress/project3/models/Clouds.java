package com.example.michaeljeffress.project3.models;

/**
 * Created by audreyeso on 8/1/16.
 */

import com.google.gson.annotations.Expose;



public class Clouds {

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
