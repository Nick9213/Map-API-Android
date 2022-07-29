package com.example.mapapi.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.mapapi.Constants.Constants;
import com.example.mapapi.R;
import com.example.mapapi.ui.fragments.HomeFragment;
import com.example.mapapi.ui.fragments.MapListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    FrameLayout fragmentView;
    HomeFragment homeFragment;
    MapListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

    }


    private void initData() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.home_bottom_nav);
        homeFragment = new HomeFragment();
        listFragment = new MapListFragment();


        //Bottom Navigation
        fragmentView = (FrameLayout) findViewById(R.id.fragment_view);
        fragmentManager = getSupportFragmentManager();
        //setFragment(homeFragment);
        Constants.loadFragment(this, homeFragment, "homeFragment");
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.bottom_nav_home:
                    Constants.loadFragment(MainActivity.this, homeFragment, "homeFragment");
                    return true;
                case R.id.bottom_nav_list:
                    Constants.loadFragment(MainActivity.this, listFragment, "listFragment");
                    return true;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_view, selectedFragment).commit();
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        builder.setMessage("Are you sure want to exit ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}