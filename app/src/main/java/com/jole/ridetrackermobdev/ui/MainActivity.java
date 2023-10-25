package com.jole.ridetrackermobdev.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import org.osmdroid.config.Configuration;

import android.Manifest;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.jole.ridetrackermobdev.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity
{
    private final int REQUEST_CODE_PERMISSION = 42;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottomNav);

        checkPermissions();
        //changed to androidx Preference Manager
        Configuration.getInstance().load(getApplicationContext(), androidx.preference.PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frFragment, MapCurrentFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("MapCurrentFragment")
                .commit();

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                int id = item.getItemId();
                if (id == R.id.nav_map)
                {
                    fragmentManager.beginTransaction()
                            .replace(R.id.frFragment, MapCurrentFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("MapCurrentFragment")
                            .commit();
                    return true;
                }
                if (id == R.id.nav_rides)
                {
                    fragmentManager.beginTransaction()
                            .replace(R.id.frFragment, TrackedRidesFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("TrackedRidesFragment")
                            .commit();
                    return true;
                }
                if (id == R.id.nav_record)
                {
                    fragmentManager.beginTransaction()
                            .replace(R.id.frFragment, RecordRideFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("TrackedRidesFragment")
                            .commit();
                    return true;
                }
                return false;
            }
        });
    }


    /**
     * Rewrote Permissions because WRITE_EXTERNAL_STORAGE is Deprecated
     */
    private void checkPermissions()
    {
        List<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            String[] perm = {Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(perm, REQUEST_CODE_PERMISSION);
        }
    }

    /**
     * Callback function for the Permissions Result
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case REQUEST_CODE_PERMISSION:
            {
                if (grantResults[0] == -1)
                {
                    Toast.makeText(this, "Location Permission is required for your location und recording your Ride", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
