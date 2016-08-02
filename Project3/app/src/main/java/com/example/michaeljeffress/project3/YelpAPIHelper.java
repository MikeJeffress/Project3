package com.example.michaeljeffress.project3;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.Coordinate;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.view.View.Y;
import static com.example.michaeljeffress.project3.R.id.textView;
import static com.example.michaeljeffress.project3.R.string.location;
import static com.google.android.gms.analytics.internal.zzy.b;
import static com.google.android.gms.analytics.internal.zzy.t;

/**
 * Created by patrickcummins on 8/1/16.
 */

public class YelpAPIHelper {
    String consumerKey, consumerSecret, token, tokenSecret;
    YelpAPI yelpAPI;
    Context context;
    ArrayList<Business> nearbyBuisnesses;
    private static OnResponseFinished onResponseFinished;

    //boolean to check retrofit synchonisity
    public interface OnResponseFinished {
        public void onBuisnessesRecieved(ArrayList<Business> buisnesses);

    }


    public YelpAPIHelper(Context context, OnResponseFinished onResponseFinished) {
        consumerKey = "";
        consumerSecret = "";
        token = "";
        tokenSecret = "";
        YelpAPIFactory apiFactory = new YelpAPIFactory(consumerKey, consumerSecret, token, tokenSecret);
        yelpAPI = apiFactory.createAPI();
        this.onResponseFinished = onResponseFinished;
        this.context = context;
    }


    //GetsBuisnesses by Params
    public void getBusinesess(Map<String, String> params, Location location) {
        CoordinateOptions coordinateOptions = CoordinateOptions.builder().latitude(location.getLatitude()).longitude(location.getLongitude()).build();

        Log.d("lat and lon for Busis", location.getLatitude() + " ," + location.getLongitude());
        Call<SearchResponse> call = yelpAPI.search(coordinateOptions, params);

        final ArrayList<Business> businesses = new ArrayList<>();


        call.enqueue(new Callback<SearchResponse>() {

            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                nearbyBuisnesses = searchResponse.businesses();
                onResponseFinished.onBuisnessesRecieved(nearbyBuisnesses);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Toast.makeText(context, "Failed To Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}