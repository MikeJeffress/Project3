package com.example.michaeljeffress.project3;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int REQUEST_CODE_LOCATION = 10;
    private static final String TAG = "MainActivity";

    private Button locationButton, typeButton;
    private EditText editText_Main_Type, editText_Main_Location;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_Map);
        mapFragment.getMapAsync(this);

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearch( );
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

//    private void saveLocation() {
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
//                    REQUEST_CODE_LOCATION);
//            return;
//        }
//    }

//    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0.0, 0.0)).title("Marker"));


    }

}
