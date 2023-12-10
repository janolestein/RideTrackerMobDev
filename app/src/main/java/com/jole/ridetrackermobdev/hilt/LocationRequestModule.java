package com.jole.ridetrackermobdev.hilt;

import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ServiceComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

/**
 * Hilt Module that provides a LocationRequest
 */
@Module
@InstallIn(ServiceComponent.class)
public class LocationRequestModule
{
    @Provides
    public LocationRequest provideFusedLocationProvider()
    {
        return new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(500)
                .setMaxUpdateDelayMillis(1000)
                .build();
    }
}
