package com.example.michaeljeffress.project3;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.ArrayAdapter;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.Coordinate;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.Y;
import static com.example.michaeljeffress.project3.R.id.textView;

/**
 * Created by patrickcummins on 8/1/16.
 */

public class YelpAPIHelper implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    String consumerKey, consumerSecret, token, tokenSecret;
    YelpAPI yelpAPI;
    ArrayList<Business> businesses = new ArrayList<>();
    GoogleApiClient mGoogleApiClient;
    Location mLocation;
    Context context;

    public YelpAPIHelper(Context context) {
        consumerKey = "M1_SfyvyDc5Ra0ULuy6D8A";
        consumerSecret = "HS72vU6uMyXlERolPnGAY6gab3s";
        token = "pmNtphuK3EzosXgDbU4j6CDhozciqqUR";
        tokenSecret = "eSy2yUWHoA2fSKg0fYvpKEslMG8";
        YelpAPIFactory apiFactory = new YelpAPIFactory(consumerKey, consumerSecret, token, tokenSecret);
        yelpAPI = apiFactory.createAPI();
        this.context = context;
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        }
        mGoogleApiClient.connect();
    }

    //Gets Names of Buisnesses
    public ArrayList<String> getBuisnessesNames(Map<String, String> params) {
        Call<SearchResponse> call = yelpAPI.search("San Francisco", params);
        final ArrayList<String> businessNames = new ArrayList<>();
        Callback<SearchResponse> callback = new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                ArrayList<Business> businesses = searchResponse.businesses();
                for (int i = 0; i < businesses.size(); i++) {
                    businessNames.add(businesses.get(i).name());
                }

            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        };
        call.enqueue(callback);
        return businessNames;
    }

    //GetsBuisnesses by Params
    public ArrayList<Business> getBusinesess(Map<String, String> params) {
        Call<SearchResponse> call = yelpAPI.search("San Francisco", params);

        Callback<SearchResponse> callback = new Callback<SearchResponse>() {

            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                businesses = searchResponse.businesses();


            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        };
        call.enqueue(callback);
        return businesses;

    }

    //GetsBuisnesses near by
    public ArrayList<Business> getBusinesessNearby(Map<String, String> params) {
        params.put("latitude", Double.toString(mLocation.getLatitude()));
        params.put("longitude", Double.toString(mLocation.getLongitude()));
        Call<SearchResponse> call = yelpAPI.search("San Francisco", params);


        Callback<SearchResponse> callback = new Callback<SearchResponse>() {

            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                businesses = searchResponse.businesses();


            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        };
        call.enqueue(callback);
        return businesses;

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
