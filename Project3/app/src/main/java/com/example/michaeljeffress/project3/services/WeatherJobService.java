package com.example.michaeljeffress.project3.services;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PersistableBundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.michaeljeffress.project3.activities.MainActivity;
import com.example.michaeljeffress.project3.interfaces.OpenWeatherInterface;
import com.example.michaeljeffress.project3.R;
import com.example.michaeljeffress.project3.models.ModelRoot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by audreyeso on 8/3/16.
 */

@TargetApi(21)

public class WeatherJobService extends JobService {

    public static final int NOTIFICATION_ID = 1;
    String baseURL = "http://api.openweathermap.org/";
    String appid = "1e2b1107da588b3b5fa83014c6555e62";
    String temp;

    protected void getCurrentWeather(PersistableBundle bundle) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            double businessLongitude = bundle.getDouble("longitude");
            double businessLatitude = bundle.getDouble("latitude");

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

                        setNotification();

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
            Toast.makeText(WeatherJobService.this, R.string.toast_not_connected, Toast.LENGTH_LONG).show();

        }

    }

    public void setNotification() {

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.barometercolor);
        mBuilder.setContentTitle(getString(R.string.content_title_notification_weather));
        mBuilder.setContentText(temp + getString(R.string.content_text_degrees));
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);
        mBuilder.setPriority(Notification.PRIORITY_DEFAULT);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        PersistableBundle bundle = jobParameters.getExtras();
        getCurrentWeather(bundle);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
