package com.jole.ridetrackermobdev.hilt;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.Priority;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ServiceComponent;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.testing.TestInstallIn;

/**
 * Hilt Module for the Location Request that overwrites the original one for testing
 */
@Module
@TestInstallIn(
        components = ServiceComponent.class,
        replaces = LocationRequestModule.class
)
public class LocationRequestModuleMock
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
