package com.example.michaeljeffress.project3;

import android.widget.ArrayAdapter;

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

public class YelpAPIHelper {
    String consumerKey, consumerSecret, token, tokenSecret;
    YelpAPI yelpAPI;
    ArrayList<Business> businesses = new ArrayList<>();
    public YelpAPIHelper() {
        consumerKey = "M1_SfyvyDc5Ra0ULuy6D8A";
        consumerSecret ="HS72vU6uMyXlERolPnGAY6gab3s";
        token = "pmNtphuK3EzosXgDbU4j6CDhozciqqUR";
        tokenSecret = "eSy2yUWHoA2fSKg0fYvpKEslMG8";
        YelpAPIFactory apiFactory = new YelpAPIFactory(consumerKey, consumerSecret, token, tokenSecret);
         yelpAPI = apiFactory.createAPI();
    }

    //Gets Names of Buisnesses
    public ArrayList<String> getBuisnessesNames(Map<String, String> params){
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
    public ArrayList<Business> getBusinesess(Map<String, String> params){
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
}
