package com.example.michaeljeffress.project3;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;
import com.yelp.clientlib.entities.Business;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, YelpAPIHelper.OnResponseFinished {
    GoogleApiClient mGoogleApiClient;
    Location mLocation;
    LocationRequest locationRequest = new LocationRequest();


    private static final int REQUEST_CODE_LOCATION = 10;
    private static final String TAG = "MainActivity";

    Button setLocationButton, setTypeButton;
    private EditText editText_Main_Type, editText_Main_Location;
    private GoogleMap mMap;
    Button weatherButton;

    private SupportMapFragment mfrag;
    YelpAPIHelper helper = new YelpAPIHelper(MainActivity.this, MainActivity.this);

    TileOverlay tileOver;
    private static final String omwURL = "http://tile.openweathermap.org/map/%s/%d/%d/%d.png";

    //Set Location Variables
    public static final int USE_ADDRESS_NAME = 1;
    public static final int USE_ADDRESS_LOCATION = 2;
    int fetchType = USE_ADDRESS_LOCATION;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTypeButton = (Button) findViewById(R.id.setTypeButton);

        mfrag = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_Map);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }



        //Get Address, Get LongLat
        editText_Main_Location = (EditText) findViewById(R.id.editText_Main_Location);
        setLocationButton = (Button) findViewById(R.id.setLocationButton);
        setLocationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                fetchType = USE_ADDRESS_NAME;
                editText_Main_Location.requestFocus();
                new GeocodeAsyncTask().execute();
            }
        });

    }


    public void onSearch() {

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
        mMap = googleMap;
        Log.d(TAG, "onMapReady: " + mLocation.getLatitude() + mLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(new LatLng(mLocation.getLatitude(), mLocation.getLongitude())).title("Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLocation.getLatitude(), mLocation.getLongitude())));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(createTilePovider()));
        HashMap<String, String> params = new HashMap<>();
        helper.getBusinesess(params, mLocation);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                1);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
        } else {
            mLocation = location;
            mfrag.getMapAsync(this);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
        } else {
            Log.d(TAG, "getLocation: " + location);
            mLocation = location;
        }
    }

    //will create a class to extend already existing location
    //yelp location extends serializable
    //android location extends parcelable

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
    public void onBuisnessesRecieved(ArrayList<Business> businesses) {


        for (int i = 0; i < businesses.size(); i++) {
            Marker currentMarker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(businesses.get(i).location().coordinate().latitude(), businesses.get(i).location().coordinate().longitude()))
                    .title(businesses.get(i).name())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
            currentMarker.setTag(businesses.get(i));


        }
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (!marker.getTitle().equals("Current Location")) {
                    Intent intent = new Intent(MainActivity.this, WeatherBusinessActivity.class);
                    intent.putExtra("business", (Business) marker.getTag());
                    startActivity(intent);
                }
            }
        });


    }

    private TileProvider createTilePovider() {
        TileProvider tileProvider = new UrlTileProvider(256, 256) {
            @Override
            public URL getTileUrl(int x, int y, int zoom) {
                String fUrl = String.format(omwURL, "temp", zoom, x, y);
                URL url = null;
                try {
                    url = new URL(fUrl);
                } catch (MalformedURLException mfe) {
                    mfe.printStackTrace();
                }
                return url;
            }
        };
        return tileProvider;
    }

    //Translate Address into Coordinates
    class GeocodeAsyncTask extends AsyncTask<Void, Void, Address> {

        String errorMessage = "";

        @Override
        protected Address doInBackground(Void... none) {
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
            List<Address> addresses = null;

            if (fetchType == USE_ADDRESS_NAME) {
                String name = editText_Main_Location.getText().toString(); //works without setting in UI thread
                try {
                    addresses = geocoder.getFromLocationName(name, 1);
                } catch (IOException e) {
                    errorMessage = "Service not available";
                    Log.e(TAG, errorMessage, e);
                }
            } else {
                errorMessage = "Unknown Type";
                Log.e(TAG, errorMessage);
            }

            if (addresses != null && addresses.size() > 0)
                return addresses.get(0);

            return null;
        }

        protected void onPostExecute(Address address) {
            String addressName = "";
            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                addressName += " --- " + address.getAddressLine(i);
            }
            editText_Main_Location.setText(addressName);

            mMap.clear();
            mLocation.setLatitude(address.getLatitude());
            mLocation.setLongitude(address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(new LatLng(mLocation.getLatitude(), mLocation.getLongitude())).title("Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLocation.getLatitude(), mLocation.getLongitude())));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            HashMap<String, String> params = new HashMap<>();
            helper.getBusinesess(params, mLocation);

        }
    }
}

