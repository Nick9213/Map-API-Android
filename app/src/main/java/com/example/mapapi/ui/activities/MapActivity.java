package com.example.mapapi.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mapapi.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity {
    SupportMapFragment supportMapFragment;
    String userName;
    Double userLatitude, userLongitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getUserDetails();
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_mapActivity);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng location = new LatLng(userLatitude, userLongitude);
                MarkerOptions options = new MarkerOptions().position(location).title(userName);
                //mMap.addMarker(new MarkerOptions().position(location).title(userName));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14F));
                googleMap.addMarker(options);
            }
        });
    }

    private void getUserDetails() {
        userName = getIntent().getStringExtra("name");
        userLatitude = Double.valueOf(getIntent().getStringExtra("userLatitude"));
        userLongitude = Double.valueOf(getIntent().getStringExtra("userLongitude"));
    }
}