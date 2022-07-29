package com.example.mapapi.ui.activities;

import static com.example.mapapi.Constants.Constants.explicitActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.example.mapapi.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding splashBinding;
    int delay = 2000; //milliseconds
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(splashBinding.getRoot());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                explicitActivity(SplashActivity.this, MainActivity.class);
                finish();
            }
        }, delay);
    }
}