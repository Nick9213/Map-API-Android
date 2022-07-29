package com.example.mapapi.Constants;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mapapi.R;

public class Constants {

    //sharedPreference
    public static final String PREF = "pref";
    // common
    public static final String CLatitude = "latitude";
    public static final String CLongitude = "longitude";

    public static void explicitActivity(Context context, Class<?> intentClass) {
        Intent intent = new Intent(context, intentClass);
        context.startActivity(intent);
    }

    ///LoadFragment
    public static void loadFragment(Context context, Fragment fragment, String fragmentName) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_view, fragment).addToBackStack(fragmentName);
        fragmentTransaction.commit();
    }
}
