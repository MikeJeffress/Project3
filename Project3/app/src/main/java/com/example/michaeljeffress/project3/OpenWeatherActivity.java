package com.example.michaeljeffress.project3;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michaeljeffress.project3.models.ModelRoot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherActivity extends AppCompatActivity {

    String baseURL = "http://api.openweathermap.org/";
    String appid = "1e2b1107da588b3b5fa83014c6555e62";
    Button searchButton;
    Button weeklyWeatherButton;
    TextView currentTemperature;
    TextView weeklyWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_weather);

        searchButton = (Button) findViewById(R.id.weather_button);
        weeklyWeatherButton = (Button) findViewById(R.id.weekly_weather_button);

        currentTemperature = (TextView) findViewById(R.id.temperature_textView);
        weeklyWeatherTextView = (TextView) findViewById(R.id.weeklly_temperature_textView);

//        weeklyWeatherButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                double longitude = 37.8344510;
//                double latitude = -122.2546560;
//
//                getWeeklyWeather(longitude, latitude);
//            }
//        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                double longitude = 37.8344510;
                double latitude = -122.2546560;
                getCurrentWeather(longitude, latitude);
            }

        });
    }

//    protected void getWeeklyWeather(double longitude, double latitude) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(baseURL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//
//            OpenWeatherInterface openWeatherInterface = retrofit.create(OpenWeatherInterface.class);
//            Call<ModelRootWeeklyWeather> call = openWeatherInterface.getWeeklyWeather(37.8344510, -122.2546560, appid);
//
//            call.enqueue(new Callback<ModelRootWeeklyWeather>() {
//                @Override
//                public void onResponse(Call<ModelRootWeeklyWeather> call, Response<ModelRootWeeklyWeather> response) {
//                    try {
//
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ModelRootWeeklyWeather> call, Throwable t) {
//                    t.printStackTrace();
//                }
//            });
//        } else {
//            Toast.makeText(OpenWeatherActivity.this, "Not connected to WIFI", Toast.LENGTH_LONG).show();
//
//        }
//    }


    protected void getCurrentWeather(double longitude, double latitude) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            OpenWeatherInterface openWeatherInterface = retrofit.create(OpenWeatherInterface.class);
            Call<ModelRoot> call = openWeatherInterface.getCurrentWeather(37.8344510, -122.2546560, appid);

            call.enqueue(new Callback<ModelRoot>() {
                @Override
                public void onResponse(Call<ModelRoot> call, Response<ModelRoot> response) {
                    try {


                        Log.d("on response", "onResponse: " + response.body().getMain().getTemp());

                        double currentTemp = response.body().getMain().getTemp().doubleValue();
                        double fahrenheit = 1.8 * (currentTemp - 273) + 32;
                        int fahrenheitInt = ((int) fahrenheit);
                        String temp = String.valueOf(fahrenheitInt);
                        currentTemperature.setText(temp + " degrees");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ModelRoot> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(OpenWeatherActivity.this, "Not connected to WIFI", Toast.LENGTH_LONG).show();

        }

    }
}
