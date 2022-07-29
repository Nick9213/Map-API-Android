package com.example.mapapi.ui.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapapi.Constants.Constants;
import com.example.mapapi.R;
import com.example.mapapi.database.DatabaseHelper;
import com.example.mapapi.models.UserLocationDetails;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    //manage session
    SharedPreferences sessionSP;

    FusedLocationProviderClient client;
    String userName, userEmail, userNumber, userLatitude, userLongitude;
    Marker userMarker;
    DatabaseHelper databaseHelper;
    SupportMapFragment supportMapFragment;
    List<UserLocationDetails> userLocationDetails = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        sessionSP = getActivity().getSharedPreferences(Constants.PREF, MODE_PRIVATE);
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        databaseHelper = new DatabaseHelper(getActivity());
        initializingNewMap();
        addMultipleUserMarker();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        initializeMap();
    }

    private void initializingNewMap() {
        Dexter.withContext(getActivity())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        initializeMap();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private void initializeMap() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                sessionSP.edit().putString(Constants.CLatitude, String.valueOf(location.getLatitude()));
                                sessionSP.edit().putString(Constants.CLongitude, String.valueOf(location.getLongitude()));
                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                MarkerOptions options = new MarkerOptions().position(latLng).title("I am here");
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                                googleMap.addMarker(options);
                            }
                        });
                    }
                }
            });

        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 44);
        }
        //Async Map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        userLatitude = String.valueOf(latLng.latitude);
                        userLongitude = String.valueOf(latLng.longitude);

                        MarkerOptions markerOptions = new MarkerOptions();
                        //position of marker
                        markerOptions.position(latLng);
                        //title of marker
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        userMarker = googleMap.addMarker(markerOptions);

                        addUserDialogBox(latLng.latitude, latLng.longitude);
                    }
                });
            }
        });
    }

    private void addUserDialogBox(double latitude, double longitude) {
        final Dialog userDialog = new Dialog(getActivity());
        userDialog.setContentView(R.layout.dialog_add_user_location);
        TextView user_latitude = (TextView) userDialog.findViewById(R.id.user_latitude);
        TextView user_longitude = (TextView) userDialog.findViewById(R.id.user_longitude);
        user_latitude.setText(String.valueOf(latitude));
        user_longitude.setText(String.valueOf(longitude));
        final EditText ed_user_name = (EditText) userDialog.findViewById(R.id.ed_user_name);
        final EditText ed_user_number = (EditText) userDialog.findViewById(R.id.ed_user_number);
        final EditText ed_user_email = (EditText) userDialog.findViewById(R.id.ed_user_email);
        TextView save_userLocation = (TextView) userDialog.findViewById(R.id.user_save_location);
        TextView close_userDialog = (TextView) userDialog.findViewById(R.id.close_userDialog);

        userDialog.show();
        userDialog.setCancelable(false);
        close_userDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDialog.dismiss();
                userMarker.remove();
            }
        });

        save_userLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = ed_user_name.getText().toString().trim();
                userEmail = ed_user_email.getText().toString().trim();
                userNumber = ed_user_number.getText().toString().trim();
                if (userName.isEmpty()) {
                    ed_user_name.setError("Enter user name");
                } else if (userEmail.isEmpty()) {
                    ed_user_email.setError("Enter email address");
                } else if (userNumber.isEmpty()) {
                    ed_user_number.setError("Enter contact number");
                } else {
                   /* Toast.makeText(getActivity(), "User name :" + userName + "\n" +
                            "Email :" + userEmail + "\n" +
                            "Contact number :" + userNumber + "\n" +
                            "User latitude :" + latitude + "\n" +
                            "User longitude :" + longitude + "\n", Toast.LENGTH_SHORT).show();*/

                    SQLiteDatabase database = databaseHelper.getReadableDatabase();
                    boolean response = databaseHelper.insertData(userName, userEmail, userNumber, userLatitude, userLongitude);
                    if (response == true) {
                        Toast.makeText(getActivity(), "User details added successfully", Toast.LENGTH_SHORT).show();
                        userDialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "User details is not added ", Toast.LENGTH_SHORT).show();
                        userDialog.dismiss();
                    }

                }
            }
        });
    }


    private void addMultipleUserMarker() {
        viewUserData();
        if (userLocationDetails.size() != 0) {
            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    for (int i = 0; i < userLocationDetails.size(); i++) {
                        LatLng location = new LatLng(Double.valueOf(userLocationDetails.get(i).getLatitude()), Double.valueOf(userLocationDetails.get(i).getLongitude()));
                        MarkerOptions options = new MarkerOptions().position(location).title(userLocationDetails.get(i).getName());
                        googleMap.addMarker(options);
                    }
                }
            });
        }
    }

    private void viewUserData() {
        userLocationDetails = new ArrayList<>();
        Cursor cursor = databaseHelper.viewData();
        if (cursor.getCount() == 0) {
            // Toast.makeText(getActivity(), "No data to show", Toast.LENGTH_SHORT).show();
        } else {
           /* if (cursor != null) {
                cursor.moveToFirst();
            }*/
            while (cursor.moveToNext()) {
                userLocationDetails.add(new UserLocationDetails(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            }

        }
    }
}