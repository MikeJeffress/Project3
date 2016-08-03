package com.example.michaeljeffress.project3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.yelp.clientlib.entities.Business;

public class WeatherBusinessActivity extends AppCompatActivity {
    Intent intent;
    Business currentBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_business);

        setCurrentBusiness();


    }

    private void setCurrentBusiness() {
        intent = getIntent();
        currentBusiness = (Business) intent.getSerializableExtra("business");
    }
}
