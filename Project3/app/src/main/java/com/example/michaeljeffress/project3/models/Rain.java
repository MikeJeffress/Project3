package com.example.michaeljeffress.project3.models;

/**
 * Created by audreyeso on 8/1/16.
 */

import com.google.gson.annotations.Expose;

public class Rain {

    @Expose
    private Double _3h;

    /**
     *
     * @return
     * The _3h
     */
    public Double get3h() {
        return _3h;
    }

    /**
     *
     * @param _3h
     * The 3h
     */
    public void set3h(Double _3h) {
        this._3h = _3h;
    }

}