package com.example.michaeljeffress.project3;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michaeljeffress.project3.jobservices.WeatherJobService;
import com.example.michaeljeffress.project3.models.ModelRoot;
import com.yelp.clientlib.entities.Business;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherBusinessActivity extends AppCompatActivity implements YelpAPIHelper.OnResponseFinished {

    private String baseURL = "http://api.openweathermap.org/";
    private String appid = "1e2b1107da588b3b5fa83014c6555e62";
    private TextView currentWeatherTextview, sunriseTextview, sunsetTextview, ratingsTextview,
            mobileTextview, isClosedTextview, businessNameTextView, locationTextview;
    private String temp;


    public static final int JOB_ID = 2;


    Intent intent;
    Business currentBusiness;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_business);

        currentWeatherTextview = (TextView) findViewById(R.id.degrees_textView);
        sunriseTextview = (TextView) findViewById(R.id.sunrise_textView);
        sunsetTextview = (TextView) findViewById(R.id.sunset_textView);
        ratingsTextview =(TextView) findViewById(R.id.ratings_textview);
        mobileTextview = (TextView) findViewById(R.id.mobile_textview);
        isClosedTextview = (TextView) findViewById(R.id.isClosed_textview);
        businessNameTextView = (TextView) findViewById(R.id.business_name_textview);
        locationTextview = (TextView) findViewById(R.id.location_address_textview);

        setCurrentBusiness();
        setUpJobService();
        getCurrentWeather();
        setTextViews();



        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {


        }
    }

    protected void getCurrentWeather() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            double businessLongitude = currentBusiness.location().coordinate().longitude();
            double businessLatitude = currentBusiness.location().coordinate().latitude();

            OpenWeatherInterface openWeatherInterface = retrofit.create(OpenWeatherInterface.class);
            Call<ModelRoot> call = openWeatherInterface.getCurrentWeather(businessLatitude, businessLongitude, appid);

            call.enqueue(new Callback<ModelRoot>() {
                @Override
                public void onResponse(Call<ModelRoot> call, Response<ModelRoot> response) {
                    try {


                        Log.d("on response", "onResponse: " + response.body().getMain().getTemp());

                        double currentTemp = response.body().getMain().getTemp().doubleValue();
                        double fahrenheit = 1.8 * (currentTemp - 273) + 32;
                        int fahrenheitInt = ((int) fahrenheit);
                        temp = String.valueOf(fahrenheitInt);
                        currentWeatherTextview.setText(temp + " degrees");

                        int sunriseTime = response.body().getSys().getSunrise();
                        String sunriseString = String.valueOf(sunriseTime);
                        sunriseTextview.setText(sunriseString);

                        int sunsetTime = response.body().getSys().getSunset();
                        String sunsetString = String.valueOf(sunsetTime);
                        sunsetTextview.setText(sunsetString);

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
            Toast.makeText(WeatherBusinessActivity.this, "Not connected to WIFI", Toast.LENGTH_LONG).show();

        }

    }


    @TargetApi(21)
    private void setUpJobService() {

        JobInfo jobInfo = new JobInfo.Builder(JOB_ID,
                new ComponentName(getPackageName(),
                        WeatherJobService.class.getName()
                )).setPeriodic(2000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);

    }


    private void setCurrentBusiness() {
        intent = getIntent();
        currentBusiness = (Business) intent.getSerializableExtra("business");
    }


    @Override
    public void onBuisnessesRecieved(ArrayList<Business> buisnesses) {

    }

    private void setTextViews() {
        boolean isClosedOrOpen = currentBusiness.isClosed();

        if (isClosedOrOpen == true) {
            String closed = "Currently closed";
            isClosedTextview.setText(closed);

        } else if (isClosedOrOpen == false) {
            String open = "Currently open";
            isClosedTextview.setText(open);
        }

        String rating = currentBusiness.rating().toString();
        String mobile = currentBusiness.displayPhone().toString();
        String businessName = currentBusiness.name();

        //fix to take away []
        String location = currentBusiness.location().address().toString();

        ratingsTextview.setText("Rating: " + rating);
        mobileTextview.setText("Phone number: " + mobile);
        businessNameTextView.setText(businessName);
        locationTextview.setText(location);

    }
}
