package com.example.mapapi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapapi.Constants.RecyclerViewClickListener;
import com.example.mapapi.R;
import com.example.mapapi.models.UserLocationDetails;

import java.util.List;

public class UserLocationDetailsAdapter extends RecyclerView.Adapter<UserLocationDetailsAdapter.ViewHolder> {

    private List<UserLocationDetails> locationDetailsList;
    private Context context;
    private View view;
    private RecyclerViewClickListener clickListener;

    public UserLocationDetailsAdapter(List<UserLocationDetails> locationDetailsList, Context context, RecyclerViewClickListener clickListener) {
        this.locationDetailsList = locationDetailsList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.user_map_details_item_container, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserLocationDetails userDetails = locationDetailsList.get(position);
        holder.tvUserName.setText("Name " + ": " + userDetails.getName());
        holder.tvUserEmail.setText("Email   " + ": " + userDetails.getEmail());
        holder.tvUserPassword.setText("Password " + ": " + userDetails.getNumber());
        holder.tvLatitude.setText("Latitude" + ": " + userDetails.getLatitude());
        holder.tvLongitude.setText("Longitude" + ": " + userDetails.getLongitude());
        Float distanceValue[] = new Float[10];
        //Location.distanceBetween(Float.valueOf(""), Float.valueOf(""), Float.valueOf(""), Float.valueOf(""), distanceValue);

        holder.tvDistance.setText(String.format("" + distanceValue));
    }

    @Override
    public int getItemCount() {
        return locationDetailsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvUserName, tvUserEmail, tvUserPassword, tvLatitude, tvLongitude, tvDistance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.user_name_item);
            tvUserEmail = itemView.findViewById(R.id.user_email_item);
            tvUserPassword = itemView.findViewById(R.id.user_number_item);
            tvLatitude = itemView.findViewById(R.id.user_latitude_item);
            tvLongitude = itemView.findViewById(R.id.user_longitude_item);
            tvDistance = itemView.findViewById(R.id.user_distance_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition());

        }
    }
}
