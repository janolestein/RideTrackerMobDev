package com.jole.ridetrackermobdev.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jole.ridetrackermobdev.R;
import com.jole.ridetrackermobdev.model.Model;
import com.jole.ridetrackermobdev.model.Ride;

import java.util.Optional;

public class RideDetailActivity extends AppCompatActivity {

    private ImageView ivRideScrennshotDetail;
    private TextView tvRideTitelDetail, tvDateDetail, tvDescDetail, tvDistanceTitelDetail, tvAvSpeedDetailTitel, tvTimeVar, tvTimeTitel, tvDistanceVarDetail, tvAvSpeedVar;
    private Button btnViewMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);

        initViews();
        int rideId = getIntent().getIntExtra("RideId", -1);
        if (rideId == -1)
        {
            Toast.makeText(this, "Something went wrong, please try again: No Ride ID found", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        Optional<Ride> ride = Model.getInstance().findRideById(rideId);

        if (!ride.isPresent()) {
            Toast.makeText(this, "Something went wrong, please try again: Could not retrieve Ride by ID", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        tvRideTitelDetail.setText(ride.map(Ride::getName).orElse(""));
        tvDateDetail.setText(ride.map(r -> r.getDate().toString()).orElse(""));
        tvDescDetail.setText(ride.map(Ride::getDescription).orElse(""));
        tvDistanceVarDetail.setText(ride.map(r -> Double.toString(r.getRideLengthKm())).orElse(""));
        tvAvSpeedVar.setText(ride.map(r -> Double.toString(r.getAverageSpeed())).orElse(""));
        tvTimeVar.setText(ride.map(r -> Double.toString(r.getTotalRideTime())).orElse(""));


        Glide.with(this)
                .asBitmap()
                .load(ride.map(Ride::getImgUrl).orElse(""))
                .into(ivRideScrennshotDetail);

        btnViewMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(RideDetailActivity.this, TrackedRideMapActivity.class);
                intent.putExtra("RideId", getIntent().getIntExtra("RideId", -1));
                startActivity(intent);

            }
        });


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
        btnViewMap = findViewById(R.id.btnViewMap);

    }
}