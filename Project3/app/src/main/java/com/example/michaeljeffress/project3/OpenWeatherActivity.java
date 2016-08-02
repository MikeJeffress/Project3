package com.example.michaeljeffress.project3;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    String baseURL = "http://api.openweathermap.org";
    String appid = "1e2b1107da588b3b5fa83014c6555e62";
    Button searchButton;
    TextView currentTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_weather);

        searchButton = (Button) findViewById(R.id.weather_button);

        currentTemperature = (TextView) findViewById(R.id.temperature_textView);

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                double longitude = 35;
                double latitude = 139;
                getCurrentWeather(longitude, latitude);
            }

        });
    }

    protected void getCurrentWeather(double longitude, double latitude) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            OpenWeatherInterface openWeatherInterface = retrofit.create(OpenWeatherInterface.class);
            Call<ModelRoot> call = openWeatherInterface.getCurrentWeather(latitude, longitude, appid);

            call.enqueue(new Callback<ModelRoot>() {
                @Override
                public void onResponse(Call<ModelRoot> call, Response<ModelRoot> response) {
                    try {
                        //  String city = response.body().getName();
                        //double longitude = response.body().getCoord().getLon();
                        //double latitude = response.body().getCoord().getLat();
                        //Main main = response.body().getMain();
                        //String currentTemp = main.getTemp().toString();
                        //String temp = String.valueOf(currentTemp);

                        //currentTemperature.setText(temp);

                        String currentTemp =response.body().getMain().getTemp().toString();
                        currentTemperature.setText(currentTemp);

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
