package com.jole.ridetrackermobdev.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.os.Build;
import android.os.Bundle;

import org.osmdroid.config.Configuration;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.jole.ridetrackermobdev.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity
{
    private final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 42;
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
        fragmentManager.beginTransaction().replace(R.id.frFragment, MapCurrentFragment.class, null).setReorderingAllowed(true).addToBackStack("MapCurrentFragment").commit();

        bottomNav.setOnItemSelectedListener(item ->
        {
            int id = item.getItemId();
            if (id == R.id.nav_map)
            {
                fragmentManager.beginTransaction().replace(R.id.frFragment, MapCurrentFragment.class, null).setReorderingAllowed(true).addToBackStack("MapCurrentFragment").commit();
                return true;
            }
            if (id == R.id.nav_rides)
            {
                fragmentManager.beginTransaction().replace(R.id.frFragment, TrackedRidesFragment.class, null).setReorderingAllowed(true).addToBackStack("TrackedRidesFragment").commit();
                return true;
            }
            if (id == R.id.nav_record)
            {
                fragmentManager.beginTransaction().replace(R.id.frFragment, RecordRideFragment.class, null).setReorderingAllowed(true).addToBackStack("TrackedRidesFragment").commit();
                return true;
            }
            return false;
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
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
        {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissions.isEmpty())
        {
            String[] params = permissions.toArray(new String[permissions.size()]);
            requestPermissions(params, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
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
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();

                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.POST_NOTIFICATIONS, PackageManager.PERMISSION_GRANTED);

                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED)
                {
                } else
                {
                    Toast.makeText(MainActivity.this, "Some Permission is Denied", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
