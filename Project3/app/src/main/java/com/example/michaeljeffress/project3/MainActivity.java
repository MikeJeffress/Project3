package com.example.michaeljeffress.project3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        YelpAPIHelper helper = new YelpAPIHelper(MainActivity.this);
        HashMap<String,String> params = new HashMap<String, String>();
        ArrayList<Business> businessNames = helper.getBusinesessNearby(params);
        ArrayList<String> businessStringNames = new ArrayList<>();

        for (int i = 0; i < businessNames.size(); i++) {
            businessStringNames.add(businessNames.get(i).name());
        }

        ArrayAdapter myAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, businessStringNames);

        ListView myListView = (ListView) findViewById(R.id.listView_Main_List);
        myListView.setAdapter(myAdapter);


    }
}
