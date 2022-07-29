package com.example.mapapi.ui.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mapapi.Constants.RecyclerViewClickListener;
import com.example.mapapi.R;
import com.example.mapapi.adapters.UserLocationDetailsAdapter;
import com.example.mapapi.database.DatabaseHelper;
import com.example.mapapi.models.UserLocationDetails;
import com.example.mapapi.ui.activities.MapActivity;

import java.util.ArrayList;
import java.util.List;

public class MapListFragment extends Fragment {

    private List<UserLocationDetails> userLocationDetails;
    DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private RecyclerViewClickListener clickListener;
    private String userLatitude, userLongitude, userName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_list, container, false);
        databaseHelper = new DatabaseHelper(getActivity());
        recyclerView = view.findViewById(R.id.mapListRecyclerView);
        viewUserData();
        setOnClickListener();
        initWidget();
        return view;
    }



    private void setOnClickListener() {
        clickListener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                userLatitude = userLocationDetails.get(position).getLatitude();
                userLongitude = userLocationDetails.get(position).getLongitude();
                userName = userLocationDetails.get(position).getName();
                Intent intent = new Intent(getActivity(), MapActivity.class);
                intent.putExtra("name", userName);
                intent.putExtra("userLatitude", userLatitude);
                intent.putExtra("userLongitude", userLongitude);
                startActivity(intent);
            }
        };
    }

    private void initWidget() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new UserLocationDetailsAdapter(userLocationDetails, getActivity(), clickListener));
    }

    void viewUserData() {
        userLocationDetails = new ArrayList<>();

        Cursor cursor = databaseHelper.viewData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "No data to show", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                userLocationDetails.add(new UserLocationDetails(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            }

        }
    }

}