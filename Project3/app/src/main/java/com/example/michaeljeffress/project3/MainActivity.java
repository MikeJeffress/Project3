package com.example.michaeljeffress.project3;

import android.support.v4.app.FragmentActivity;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yelp.clientlib.entities.Business;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, YelpAPIHelper.OnResponseFinished {
    GoogleApiClient mGoogleApiClient;
    Location mLocation;
    Button getLocation;
    LocationRequest locationRequest;

    private static final int REQUEST_CODE_LOCATION = 10;
    private static final String TAG = "MainActivity";

    private Button locationButton, typeButton;
    private EditText editText_Main_Type, editText_Main_Location;
    private GoogleMap mMap;

    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_Map);
        mapFragment.getMapAsync(this);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }

        getLocation = (Button) findViewById(R.id.locationButton);
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                YelpAPIHelper helper = new YelpAPIHelper(MainActivity.this, MainActivity.this);
                HashMap<String, String> params = new HashMap<String, String>();

                helper.getBusinesess(params, mLocation);
            }
        });
    }


    public void onSearch() {
        editText_Main_Location = (EditText) findViewById(R.id.editText_Main_Location);
        String location = editText_Main_Location.getText().toString();
        List<Address> addressList = null;
        if (location != null || location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in " + location));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

//        commented this out in order to build gradle

//        mMap.addMarker(new MarkerOptions().position(new LatLng(0.0, 0.0)).title("Marker"));



    }




    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
        }
        else {
            mLocation = location;
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public ArrayList<String> setUpBusinessNames(ArrayList<Business> businesses) {
        ArrayList<String> businessStringNames = new ArrayList<String>();



        for (int i = 0; i < businesses.size(); i++) {
            businessStringNames.add(businesses.get(i).name());
        }


        return businessStringNames;
    }

    public void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);


        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
        }
        else {
            mLocation = location;
        }
    }

    @Override
    protected void onStart() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
    }


    @Override
    public void onBuisnessesRecieved(ArrayList<Business> buisnesses) {

        ArrayList<String> businessStringNames = setUpBusinessNames(buisnesses);









    }
}

