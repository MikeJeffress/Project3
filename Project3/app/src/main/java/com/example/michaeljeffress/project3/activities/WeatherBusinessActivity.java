package com.example.michaeljeffress.project3.activities;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michaeljeffress.project3.R;
import com.example.michaeljeffress.project3.helpers.YelpAPIHelper;
import com.example.michaeljeffress.project3.interfaces.OpenWeatherInterface;
import com.example.michaeljeffress.project3.services.WeatherJobService;
import com.example.michaeljeffress.project3.models.ModelRoot;
import com.squareup.picasso.Picasso;
import com.yelp.clientlib.entities.Business;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherBusinessActivity extends AppCompatActivity implements YelpAPIHelper.OnResponseFinished {

    private String baseURL = "http://api.openweathermap.org/";
    private String appid = "1e2b1107da588b3b5fa83014c6555e62";
    private TextView currentWeatherTextview, sunriseTextview, sunsetTextview,
            mobileTextview, isClosedTextview, businessNameTextView, locationTextview, cityTextView, zipCodeTextView;
    private ImageView yelpImageView;
    private String temp;
    private RatingBar ratingBar;
    private static final String TAG = WeatherBusinessActivity.class.getSimpleName();
    public static final int JOB_ID = 2;

    Intent intent;
    Business currentBusiness;
    String imageUrl;
    int sunriseTime;
    int sunsetTime;
    double currentTemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_business);

        setUpConnectivity();
        findViewById();
        setCurrentBusiness();
        getCurrentWeather();
        setUpJobService();
        setTextViews();
    }

    private void setUpConnectivity() {
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

                        currentTemp = response.body().getMain().getTemp().doubleValue();
                        getFahrenheit();
                        sunriseTime = response.body().getSys().getSunrise();
                        sunsetTime = response.body().getSys().getSunset();

                        setUpSunriseSunset();

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
            Toast.makeText(WeatherBusinessActivity.this, R.string.toast_not_connected_wifi, Toast.LENGTH_LONG).show();

        }

    }

    private void getFahrenheit() {
        double fahrenheit = 1.8 * (currentTemp - 273) + 32;
        int fahrenheitInt = ((int) fahrenheit);
        temp = String.valueOf(fahrenheitInt);
        currentWeatherTextview.setText(getString(R.string.current_temp_text) + temp + (char) 0x00B0);
    }

    private void setUpSunriseSunset() {

        long sunriseLong = ((long) sunriseTime);
        long sunriseTimestamp = sunriseLong * 1000L;
        Date date = new Date(sunriseTimestamp);
        String sunriseString = String.valueOf(date);
        sunriseTextview.setText(getString(R.string.sunrise_string) + sunriseString);

        long sunsetLong = ((long) sunsetTime);
        long sunsetTimestamp = sunsetLong * 1000L;
        Date sunsetDate = new Date(sunsetTimestamp);
        String sunsetString = String.valueOf(sunsetDate);
        sunsetTextview.setText(getString(R.string.sunset_string) + sunsetString);
    }


    @TargetApi(21)
    private void setUpJobService() {
        PersistableBundle bundle = new PersistableBundle();
        bundle.putDouble("latitude", currentBusiness.location().coordinate().latitude());
        bundle.putDouble("longitude", currentBusiness.location().coordinate().latitude());

        JobInfo jobInfo = new JobInfo.Builder(JOB_ID,
                new ComponentName(getPackageName(),
                        WeatherJobService.class.getName()
                )).setPeriodic(3600000).setExtras(bundle)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);

    }


    private void setCurrentBusiness() {
        intent = getIntent();
        currentBusiness = (Business) intent.getSerializableExtra("business");
    }

    private void findViewById() {

        currentWeatherTextview = (TextView) findViewById(R.id.degrees_textView);
        sunriseTextview = (TextView) findViewById(R.id.sunrise_textView);
        sunsetTextview = (TextView) findViewById(R.id.sunset_textView);
        mobileTextview = (TextView) findViewById(R.id.mobile_textview);
        isClosedTextview = (TextView) findViewById(R.id.isClosed_textview);
        businessNameTextView = (TextView) findViewById(R.id.business_name_textview);
        locationTextview = (TextView) findViewById(R.id.location_address_textview);
        yelpImageView = (ImageView) findViewById(R.id.business_imageView);
        cityTextView = (TextView) findViewById(R.id.location_city_textview);
        zipCodeTextView = (TextView) findViewById(R.id.location_zipcode_textview);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
    }

    @Override
    public void onBuisnessesRecieved(ArrayList<Business> buisnesses) {
    }

    private void setTextViews() {

        boolean isClosedOrOpen = currentBusiness.isClosed();

        if (isClosedOrOpen == true) {
            String closed = getString(R.string.current_business_closed);
            isClosedTextview.setText(closed);

        } else if (isClosedOrOpen == false) {
            String open = getString(R.string.current_business_open);
            isClosedTextview.setText(open);
        }

        double ratingDouble = (currentBusiness.rating());
        float ratingFloat = (float) ratingDouble;
        Drawable progress = ratingBar.getProgressDrawable();
        DrawableCompat.setTint(progress, Color.WHITE);
        ratingBar.setRating(ratingFloat);

        String mobile = currentBusiness.displayPhone().toString();
        String businessName = currentBusiness.name();
        imageUrl = currentBusiness.imageUrl().toString();
        String loc = currentBusiness.location().address().toString();
        String location = loc.substring(1, loc.length() - 1);
        String cityString = currentBusiness.location().city().toString();
        String stateString = currentBusiness.location().stateCode().toString();
        String zipCodeString = currentBusiness.location().postalCode().toString();

        mobileTextview.setText(getString(R.string.mobile_textview_phone) + mobile);
        businessNameTextView.setText(businessName);
        locationTextview.setText(location);
        cityTextView.setText(cityString + "," + stateString);
        zipCodeTextView.setText(zipCodeString);

        setUpPicasso();

    }

    public void setUpPicasso() {
        Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {

            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        }).build();
        picasso.load(imageUrl).into(yelpImageView, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Success");
            }

            @Override
            public void onError() {
                Log.d(TAG, "No Success");

            }

        });
    }
}