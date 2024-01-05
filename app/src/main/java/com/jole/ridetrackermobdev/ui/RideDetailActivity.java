package com.jole.ridetrackermobdev.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jole.ridetrackermobdev.R;
import com.jole.ridetrackermobdev.controller.MainFragmentsViewModel;
import com.jole.ridetrackermobdev.controller.RideDetailViewModel;
import com.jole.ridetrackermobdev.model.DaoInterface;

import com.jole.ridetrackermobdev.model.Ride;
import com.jole.ridetrackermobdev.model.RideRepository;
import com.jole.ridetrackermobdev.model.RideRepositoryInterface;

import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Shows the Details of a selected Ride
 */
@AndroidEntryPoint
public class RideDetailActivity extends AppCompatActivity
{
    private ImageView ivRideScrennshotDetail;
    private TextView tvRideTitelDetail, tvDateDetail, tvDescDetail, tvDistanceTitelDetail, tvAvSpeedDetailTitel, tvTimeVar, tvTimeTitel, tvDistanceVarDetail, tvAvSpeedVar;
    private Button btnViewMap;
    private int rideId;
    private RideDetailViewModel rideDetailViewModel;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);
        rideDetailViewModel = new ViewModelProvider(this).get(RideDetailViewModel.class);

        initViews();
        rideId = getIntent().getIntExtra("RideId", -1);
        Log.v("Test", Integer.toString(rideId));
        if (rideId == -1)
        {
            Toast.makeText(this, "Something went wrong, please try again: No Ride ID found", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        Optional<Ride> ride = rideDetailViewModel.findRideById(rideId);

        if (!ride.isPresent())
        {
            Toast.makeText(this, "Something went wrong, please try again: Could not retrieve Ride by ID", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        tvRideTitelDetail.setText(ride.map(Ride::getName).orElse(""));
        tvDateDetail.setText(ride.map(r -> r.getDate()).orElse(""));
        tvDistanceVarDetail.setText(ride.map(r -> String.format(Locale.GERMANY,"%.2f", r.getRideLengthKm()) + " km").orElse(""));
        tvAvSpeedVar.setText(ride.map(r -> String.format(Locale.GERMANY,"%.2f", r.getAverageSpeed()) + " km/h").orElse(""));
        tvTimeVar.setText(ride.map(r -> String.format(Locale.GERMANY,"%dh, %dmin, %dsec",
                TimeUnit.MILLISECONDS.toHours((long) r.getTotalRideTime()),
                TimeUnit.MILLISECONDS.toMinutes((long) r.getTotalRideTime()),
                TimeUnit.MILLISECONDS.toSeconds((long) r.getTotalRideTime()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) r.getTotalRideTime()))
        )).orElse(""));


        btnViewMap.setOnClickListener(v ->
        {
            Intent intent = new Intent(RideDetailActivity.this, TrackedRideMapActivity.class);
            intent.putExtra("rideId", rideId);
            startActivity(intent);

        });


    }

    private void initViews()
    {
        tvRideTitelDetail = findViewById(R.id.tvRideTitelDetail);
        tvDateDetail = findViewById(R.id.tvDateDetail);
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