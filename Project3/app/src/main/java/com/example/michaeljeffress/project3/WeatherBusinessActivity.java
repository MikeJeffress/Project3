package com.example.michaeljeffress.project3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yelp.clientlib.entities.Business;

import java.util.ArrayList;
import java.util.HashMap;

public class WeatherBusinessActivity extends AppCompatActivity implements YelpAPIHelper.OnResponseFinished{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_business);

        YelpAPIHelper yelpAPIHelper = new YelpAPIHelper(WeatherBusinessActivity.this, WeatherBusinessActivity.this);
        HashMap<String, String> hashMapParams = new HashMap<String, String>();
        //yelpAPIHelper.getBusinesess(hashMapParams,currentLocation); //get location
    }

    @Override
    public void onBuisnessesRecieved(ArrayList<Business> buisnesses) {
        Business currentBusiness = buisnesses.get(0);
        currentBusiness.name();
        currentBusiness.imageUrl();
        currentBusiness.mobileUrl();
        currentBusiness.rating();
        currentBusiness.reviews();
        currentBusiness.isClosed();
        currentBusiness.location().address();

    }
}
