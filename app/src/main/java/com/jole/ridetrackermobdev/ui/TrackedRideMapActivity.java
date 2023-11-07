package com.jole.ridetrackermobdev.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.jole.ridetrackermobdev.R;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.CopyrightOverlay;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;


/**
 * This Activity only exists because i better know how to create a Map in a Fragment
 * TODO: Change that
 */
public class TrackedRideMapActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracked_ride_map);
        Bundle bundle = new Bundle();
        bundle.putInt("rideId", getIntent().getIntExtra("rideId", -1));
        int rideId = getIntent().getIntExtra("rideId", -1);

        Log.v("ABC", Integer.toString(rideId));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.trackedMapFragment, TrackedRideMapFragment.newInstance(getIntent().getIntExtra("rideId", -1)))
                .commit();
    }
}