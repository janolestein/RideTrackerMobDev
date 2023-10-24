package com.jole.ridetrackermobdev.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jole.ridetrackermobdev.R;
import com.jole.ridetrackermobdev.model.Model;
import com.jole.ridetrackermobdev.model.Ride;

public class RideDetailActivity extends AppCompatActivity {

    private ImageView ivRideScrennshotDetail;
    private TextView tvRideTitelDetail, tvDateDetail, tvDescDetail, tvDistanceTitelDetail, tvAvSpeedDetailTitel, tvTimeVar, tvTimeTitel, tvDistanceVarDetail, tvAvSpeedVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);

        initViews();
        int rideId = getIntent().getIntExtra("RideId", -1);
        if (rideId == -1)
        {
            Toast.makeText(this, "Something went wrong, please try again: No Ride ID found", Toast.LENGTH_SHORT).show();
        }
        Ride ride = Model.getInstance().findRideById(rideId);
        if (ride == null) {
            Toast.makeText(this, "Something went wrong, please try again: Could not retrieve Ride by ID", Toast.LENGTH_SHORT).show();
        }

        tvRideTitelDetail.setText(ride.getName());
        tvDateDetail.setText(ride.getDate().toString());
        tvDescDetail.setText(ride.getDescription());
        tvDistanceVarDetail.setText(Double.toString(ride.getRideLengthKm()));
        tvAvSpeedVar.setText(Double.toString(ride.getAverageSpeed()));
        tvTimeVar.setText(Double.toString(ride.getTotalRideTime()));

        Glide.with(this)
                .asBitmap()
                .load(ride.getImgUrl())
                .into(ivRideScrennshotDetail);



    }

    private void initViews() {
        ivRideScrennshotDetail = findViewById(R.id.ivRideScrennshotDetail);
        tvRideTitelDetail = findViewById(R.id.tvRideTitelDetail);
        tvDateDetail = findViewById(R.id.tvDateDetail);
        tvDescDetail = findViewById(R.id.tvDescDetail);
        tvDistanceTitelDetail = findViewById(R.id.tvDistanceTitelDetail);
        tvAvSpeedDetailTitel = findViewById(R.id.tvAvSpeedDetailTitel);
        tvTimeVar = findViewById(R.id.tvTimeVar);
        tvTimeTitel = findViewById(R.id.tvTimeTitel);
        tvTimeTitel = findViewById(R.id.tvTimeTitel);
        tvDistanceVarDetail = findViewById(R.id.tvDistanceVarDetail);
        tvAvSpeedVar = findViewById(R.id.tvAvSpeedVar);

    }
}